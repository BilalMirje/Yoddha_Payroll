package com.standard.service.impl;

import com.standard.entity.Contractor;
import com.standard.entity.ContractorAdvance;
import com.standard.entity.ContractorEvent;
import com.standard.entity.dtos.contractorAdvance.ContractorAdvanceHistoryResponse;
import com.standard.entity.dtos.contractorAdvance.ContractorAdvanceRequest;
import com.standard.entity.dtos.contractorAdvance.ContractorAdvanceRow;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.exceptions.handlers.ResourceNotFoundException;
import com.standard.mappers.ContractorAdvanceMapper;
import com.standard.payload.ApiResponse;
import com.standard.payload.ApiResponseUtil;
import com.standard.queryHelper.QueryResultHandler;
import com.standard.repository.ContractorAdvanceRepository;
import com.standard.repository.ContractorEventRepository;
import com.standard.repository.ContractorRepository;
import com.standard.service.ContractorAdvanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractorAdvanceServiceImpl implements ContractorAdvanceService {

    private final ContractorAdvanceRepository advanceRepository;
    private final ContractorEventRepository contractorEventRepository;
    private final ContractorAdvanceMapper mapper;
    private final QueryResultHandler queryResultHandler;

    @Override
    public ApiResponse<?> create(ContractorAdvanceRequest request) {

        ContractorEvent ce = contractorEventRepository
                .findById(request.getContractorEventId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contractor Event not found"));

        ContractorAdvance advance = mapper.toEntity(ce, request);

        advanceRepository.save(advance);

        return ApiResponseUtil.created(mapper.toResponse(advance));
    }

    @Override
    public ApiResponse<?> update(Long id, ContractorAdvanceRequest request) {

        ContractorAdvance advance = advanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Advance record not found"));

        ContractorEvent ce = contractorEventRepository
                .findById(request.getContractorEventId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contractor Event not found"));

        mapper.updateEntity(advance, request, ce);

        return ApiResponseUtil.updated(mapper.toResponse(advance));
    }

    @Override
    public ApiResponse<?> getById(Long id) {
        return advanceRepository.findById(id)
                .map(a -> ApiResponseUtil.fetched(mapper.toResponse(a)))
                .orElseThrow(() -> new ResourceNotFoundException("Advance not found"));
    }

    @Override
    public ApiResponse<?> getAll(PageRequest pageRequest) {

        return queryResultHandler.fetchAndRespond(
                pageRequest,
                pageable -> advanceRepository.findAll(pageable),
                sort -> advanceRepository.findAll(sort),
                () -> advanceRepository.findAll(),
                mapper::toResponse,
                "no record found"
        );
    }

    @Override
    public ApiResponse<?> delete(Long id) {

        ContractorAdvance advance = advanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Advance not found"));

        advanceRepository.delete(advance);

        return ApiResponseUtil.deleted();
    }

    @Override
    public ApiResponse<?> getAdvanceHistoryByContractor(Long contractorId) {

        var advances = advanceRepository.findByContractorEvent_Contractor_Id(contractorId);

        if (advances.isEmpty())
            throw new ResourceNotFoundException("No advance history found for contractor");

        double totalAdvance = advances.stream()
                .mapToDouble(ContractorAdvance::getAmount)
                .sum();

        var contractor = advances.get(0)
                .getContractorEvent()
                .getContractor();

        var rows = advances.stream()
                .map(mapper::toHistoryRow)
                .toList();

        var response = ContractorAdvanceHistoryResponse.builder()
                .contractorId(contractor.getId())
                .contractorName(contractor.getName())
                .totalAdvanceGiven(totalAdvance)
                .advances(rows)
                .build();

        return ApiResponseUtil.fetched(response);
    }

}