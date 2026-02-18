package com.standard.service.impl;

import com.standard.entity.Contractor;
import com.standard.entity.ContractorEvent;
import com.standard.entity.ContractorPayment;
import com.standard.entity.Event;
import com.standard.entity.dtos.contractorPayment.ContractorPaymentHistoryResponse;
import com.standard.entity.dtos.eventWorkItem.*;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.exceptions.handlers.ResourceNotFoundException;
import com.standard.mappers.ContractorEventLedgerMapper;
import com.standard.mappers.ContractorEventMapper;
import com.standard.mappers.ContractorPaymentMapper;
import com.standard.payload.ApiResponse;
import com.standard.payload.ApiResponseUtil;
import com.standard.queryHelper.QueryResultHandler;
import com.standard.repository.*;
import com.standard.service.ContractorEventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@Transactional
public class ContractorEventServiceImpl implements ContractorEventService {

    private final ContractorEventRepository contractorEventRepository;
    private final ContractorRepository contractorRepository;
    private final EventRepository eventRepository;
    private final ContractorAdvanceRepository contractorAdvanceRepository;
    private final QueryResultHandler queryResultHandler;
    private final ContractorEventMapper mapper;
    private final ContractorEventLedgerMapper ledgerMapper;
    private final ContractorPaymentRepository contractorPaymentRepository;
    private final ContractorPaymentMapper paymentMapper;


    // ---------- ASSIGN CONTRACTOR ----------
    @Override
    public ApiResponse<?> assignContractor(ContractorEventRequest request) {

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        // ---------- BULK ASSIGN ----------
        if (request.getContractorIds() != null && !request.getContractorIds().isEmpty()) {

            var responses = request.getContractorIds().stream().map(id -> {

                if (contractorEventRepository
                        .existsByEvent_IdAndContractor_Id(event.getId(), id)) {
                    throw new ResourceNotFoundException(
                            "Contractor already assigned to this event: id=" + id
                    );
                }

                Contractor contractor = contractorRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Contractor not found: id=" + id));

                ContractorEvent ce = mapper.createEntity(event, contractor);
                contractorEventRepository.save(ce);

                return mapper.toSummary(ce);

            }).toList();

            return ApiResponseUtil.created(responses);
        }

        // ---------- SINGLE ASSIGN ----------
        if (contractorEventRepository
                .existsByEvent_IdAndContractor_Id(request.getEventId(), request.getContractorId())) {

            throw new ResourceNotFoundException(
                    "This contractor is already assigned to this event"
            );
        }

        Contractor contractor = contractorRepository.findById(request.getContractorId())
                .orElseThrow(() -> new ResourceNotFoundException("Contractor not found"));

        ContractorEvent ce = mapper.createEntity(event, contractor);
        contractorEventRepository.save(ce);

        return ApiResponseUtil.created(mapper.toSummary(ce));
    }



    // ---------- GET BY ID ----------
    @Override
    public ApiResponse<?> getById(Long contractorEventId) {

        return contractorEventRepository.findById(contractorEventId)
                .map(ce -> ApiResponseUtil.fetched(mapper.toSummary(ce)))
                .orElseThrow(() -> new ResourceNotFoundException("Contractor Event not found"));
    }

    // ---------- GET ALL CONTRACTORS OF EVENT ----------
    @Override
    public ApiResponse<?> getAllByEvent(Long eventId, PageRequest pageRequest) {

        return queryResultHandler.fetchAndRespond(
                pageRequest,
                pageable -> contractorEventRepository.findByEventId(eventId, pageable),
                sort -> contractorEventRepository.findByEventId(eventId, sort),
                () -> contractorEventRepository.findByEventId(eventId),
                ce -> {

                    Double advance = contractorAdvanceRepository
                            .sumByContractorEventId(ce.getId())
                            .orElse(0.0);

                    double netRemaining =
                            ce.getTotalAmount() - advance - ce.getTotalPaid();

                    return mapper.toLedgerResponse(ce, advance, netRemaining);
                },
                "no contractor assigned for this event"
        );
    }

    // ---------- UPDATE ASSIGNED CONTRACTOR ----------
    @Override
    public ApiResponse<?> updateAssignedContractor(Long contractorEventId,
                                                   ContractorEventRequest request) {

        ContractorEvent ce = contractorEventRepository.findById(contractorEventId)
                .orElseThrow(() -> new ResourceNotFoundException("Contractor Event not found"));

        if (ce.getContractor().getId().equals(request.getContractorId())) {
            return ApiResponseUtil.updated("No change — contractor already assigned");
        }

        Contractor newContractor = contractorRepository.findById(request.getContractorId())
                .orElseThrow(() -> new ResourceNotFoundException("Contractor not found"));

        ce.setContractor(newContractor);

        contractorEventRepository.save(ce);

        return ApiResponseUtil.updated(mapper.toSummary(ce));
    }

    // ---------- DELETE ----------
    @Override
    public ApiResponse<?> deleteContractorEvent(Long contractorEventId) {

        if (!contractorEventRepository.existsById(contractorEventId))
            throw new ResourceNotFoundException("Contractor Event not found");

        contractorEventRepository.deleteById(contractorEventId);

        return ApiResponseUtil.deleted();
    }

    // ---------- CONTRACTOR LEVEL LEDGER ----------

    @Override
    public ApiResponse<?> getContractorLedger(Long contractorId) {

        Contractor contractor = contractorRepository.findById(contractorId)
                .orElseThrow(() -> new ResourceNotFoundException("Contractor not found"));

        var contractorEvents = contractorEventRepository.findByContractor_Id(contractorId);

        if (contractorEvents.isEmpty())
            throw new ResourceNotFoundException("No event history found for contractor");

        var rows = contractorEvents.stream().map(ce -> {

            Double advance = contractorAdvanceRepository
                    .sumByContractorEventId(ce.getId())
                    .orElse(0.0);

            double netRemaining =
                    ce.getTotalAmount() - advance - ce.getTotalPaid();

            return ledgerMapper.toLedgerRow(ce, advance, netRemaining);

        }).toList();

        var response = ledgerMapper.toFullLedgerResponse(contractor, rows);

        return ApiResponseUtil.fetched(response);
    }

    // ---------- PAYMENT ----------
    @Override
    public ApiResponse<?> payToContractor(ContractorPaymentRequest request) {

        ContractorEvent ce = contractorEventRepository.findById(request.getContractorEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Contractor Event not found"));

        // 1) Save payment log
        ContractorPayment payment = ContractorPayment.builder()
                .contractorEvent(ce)
                .amount(request.getAmount())
                .paymentDate(request.getPaymentDate())   // <- add in request DTO
                .remark(request.getRemark())
                .build();

        contractorPaymentRepository.save(payment);

        // 2) Update totals
        ce.setTotalPaid(ce.getTotalPaid() + request.getAmount());
        ce.setTotalRemaining(ce.getTotalAmount() - ce.getTotalPaid());

        contractorEventRepository.save(ce);

        return ApiResponseUtil.updated("Payment recorded successfully");
    }

    @Override
    public ApiResponse<?> getPaymentHistoryByContractor(Long contractorId) {

        var payments =
                contractorPaymentRepository.findByContractorEvent_Contractor_IdOrderByPaymentDateAsc(contractorId);

        if (payments.isEmpty())
            throw new ResourceNotFoundException("No payment history found for contractor");

        double totalPaid = payments.stream()
                .mapToDouble(p -> p.getAmount())
                .sum();

        var contractor = payments.get(0).getContractorEvent().getContractor();

        var rows = payments.stream()
                .map(paymentMapper::toRow)
                .toList();

        var response = ContractorPaymentHistoryResponse.builder()
                .contractorId(contractor.getId())
                .contractorName(contractor.getName())
                .totalPaid(totalPaid)
                .payments(rows)
                .build();

        return ApiResponseUtil.fetched(response);
    }


}
