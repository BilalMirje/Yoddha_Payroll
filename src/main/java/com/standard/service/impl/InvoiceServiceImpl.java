    package com.standard.service.impl;

    import com.standard.entity.ContractorEvent;
    import com.standard.entity.Event;
    import com.standard.entity.EventWorkItem;
    import com.standard.entity.dtos.eventWorkItem.*;
    import com.standard.entity.dtos.ledger.ContractorInvoiceResponse;
    import com.standard.entity.dtos.ledger.EventInvoiceResponse;
    import com.standard.entity.dtos.ledger.EventWorkItemInvoiceResponse;
    import com.standard.exceptions.handlers.ResourceNotFoundException;
    import com.standard.payload.ApiResponse;
    import com.standard.payload.ApiResponseUtil;
    import com.standard.repository.ContractorAdvanceRepository;
    import com.standard.repository.ContractorEventRepository;
    import com.standard.repository.EventRepository;
    import com.standard.repository.EventWorkItemRepository;
    import com.standard.service.InvoiceService;
    import jakarta.transaction.Transactional;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.util.List;


    @Service
    @RequiredArgsConstructor
    @Transactional
    public class InvoiceServiceImpl implements InvoiceService {

        private final EventRepository eventRepository;
        private final ContractorEventRepository contractorEventRepository;
        private final EventWorkItemRepository workItemRepository;
        private final ContractorAdvanceRepository contractorAdvanceRepository;


        // ---------------- EVENT WISE INVOICE ----------------
        @Override
        public ApiResponse<?> generateEventInvoice(Long eventId) {

            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

            List<ContractorEvent> contractorLinks =
                    contractorEventRepository.findByEventId(eventId);

            List<EventWorkItemResponse.EventContractorInvoiceBlock> contractors = contractorLinks.stream()
                    .map(ce -> {

                        List<EventWorkItem> items =
                                workItemRepository.findByContractorEventId(ce.getId());

                        List<EventWorkItemInvoiceResponse> itemDtos = items.stream()
                                .map(w -> EventWorkItemInvoiceResponse.builder()
                                        .id(w.getId())
                                        .description(w.getDescription())
                                        .entryType(w.getEntryType())
                                        .labourCount(w.getLabourCount())
                                        .perLabourWage(w.getPerLabourWage())
                                        .quantity(w.getQuantity())
                                        .unitCost(w.getUnitCost())
                                        .totalAmount(w.getTotalAmount())
                                        .paid(w.getPaid())
                                        .remaining(w.getRemaining())
                                        .build()
                                ).toList();

                        return EventWorkItemResponse.EventContractorInvoiceBlock.builder()
                                .contractorName(ce.getContractor().getName())
                                .totalAmount(ce.getTotalAmount())
                                .paid(ce.getTotalPaid())
                                .remaining(ce.getTotalRemaining())
                                .items(itemDtos)
                                .build();
                    })
                    .toList();

            // ---- compute totals here (safe & clean)
            double grandTotal = contractors.stream()
                    .mapToDouble(EventWorkItemResponse.EventContractorInvoiceBlock::getTotalAmount)
                    .sum();

            double grandPaid = contractors.stream()
                    .mapToDouble(EventWorkItemResponse.EventContractorInvoiceBlock::getPaid)
                    .sum();

            double grandRemaining = contractors.stream()
                    .mapToDouble(EventWorkItemResponse.EventContractorInvoiceBlock::getRemaining)
                    .sum();

            EventInvoiceResponse response = EventInvoiceResponse.builder()
                    .eventName(event.getEventName())
                    .eventLocation(event.getLocation())
                    .eventDate(event.getEventDate())
                    .contractors(contractors)
                    .grandTotal(grandTotal)
                    .grandPaid(grandPaid)
                    .grandRemaining(grandRemaining)
                    .build();

            return ApiResponseUtil.fetched(response);
        }


        // ---------------- CONTRACTOR WISE INVOICE ----------------
        @Override
        public ApiResponse<?> generateContractorInvoice(Long contractorEventId) {

            ContractorEvent ce = contractorEventRepository.findById(contractorEventId)
                    .orElseThrow(() -> new ResourceNotFoundException("Contractor Event not found"));

            //  Fetch work items
            List<EventWorkItem> items =
                    workItemRepository.findByContractorEventId(contractorEventId);

            List<EventWorkItemInvoiceResponse> itemDtos = items.stream()
                    .map(mapper -> EventWorkItemInvoiceResponse.builder()
                            .id(mapper.getId())
                            .description(mapper.getDescription())
                            .entryType(mapper.getEntryType())
                            .shiftType(mapper.getShiftType())
                            .labourCount(mapper.getLabourCount())
                            .perLabourWage(mapper.getPerLabourWage())
                            .quantity(mapper.getQuantity())
                            .unitCost(mapper.getUnitCost())
                            .totalAmount(mapper.getTotalAmount())
                            .paid(mapper.getPaid())
                            .remaining(mapper.getRemaining())
                            .build()
                    ).toList();

            // Get ADVANCE for THIS event only
            Double totalAdvance = contractorAdvanceRepository
                    .sumByContractorEventId(contractorEventId)
                    .orElse(0.0);

            //  Compute Net Remaining
            double netRemaining =
                    ce.getTotalAmount() - totalAdvance - ce.getTotalPaid();

            ContractorInvoiceResponse response = ContractorInvoiceResponse.builder()
                    .eventName(ce.getEvent().getEventName())
                    .eventDate(ce.getEvent().getEventDate())
                    .contractorName(ce.getContractor().getName())
                    .aadhar(ce.getContractor().getAadharNumber())
                    .contact(ce.getContractor().getContact())
                    .totalAmount(ce.getTotalAmount())
                    .paid(ce.getTotalPaid())
                    .remaining(ce.getTotalAmount()- ce.getTotalPaid())
                    .totalAdvance(totalAdvance)
                    .netRemaining(netRemaining)
                    .items(itemDtos)
                    .build();

            return ApiResponseUtil.fetched(response);
        }

    }
