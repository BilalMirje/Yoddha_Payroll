package com.standard.service.impl;

import com.standard.entity.ContractorEvent;
import com.standard.entity.EventWorkItem;
import com.standard.entity.dtos.eventWorkItem.EventWorkItemRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.exceptions.handlers.ResourceNotFoundException;
import com.standard.mappers.EventWorkItemMapper;
import com.standard.payload.ApiResponse;
import com.standard.payload.ApiResponseUtil;
import com.standard.queryHelper.QueryResultHandler;
import com.standard.repository.ContractorEventRepository;
import com.standard.repository.EventWorkItemRepository;
import com.standard.service.EventWorkItemService;
import com.standard.service.helper.ContractorEventTotalHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class EventWorkItemServiceImpl implements EventWorkItemService {

    private final EventWorkItemRepository workItemRepository;
    private final ContractorEventRepository contractorEventRepository;
    private final ContractorEventTotalHelper totalHelper;
    private final EventWorkItemMapper mapper;
    private final QueryResultHandler queryResultHandler;


    // ===================================================================
    // ADD WORK ITEM  (Single + Bulk)
    // ===================================================================
    @Override
    public ApiResponse<?> addWorkItem(EventWorkItemRequest request) {

        ContractorEvent ce = contractorEventRepository.findById(request.getContractorEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Contractor Event not found"));

        // ---------- BULK ADD ----------
        if (request.getItems() != null && !request.getItems().isEmpty()) {

            var added = request.getItems().stream().map(r -> {

                double total = calculateTotal(r);
                double remaining = total - r.getPaid();

                EventWorkItem item = mapper.toEntity(r, ce, total, remaining);
                workItemRepository.save(item);

                return mapper.toResponse(item);

            }).toList();

            totalHelper.recalculateTotals(ce);
            return ApiResponseUtil.created(added);
        }

        // ---------- SINGLE ADD ----------
        double total = calculateTotal(request);
        double remaining = total - request.getPaid();

        EventWorkItem item = mapper.toEntity(request, ce, total, remaining);
        workItemRepository.save(item);

        totalHelper.recalculateTotals(ce);

        return ApiResponseUtil.created(mapper.toResponse(item));
    }


    // ===================================================================
    // UPDATE WORK ITEM  (Single + Bulk)
    // ===================================================================
    @Override
    public ApiResponse<?> updateWorkItem(EventWorkItemRequest request) {

        ContractorEvent ce = contractorEventRepository.findById(request.getContractorEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Contractor Event not found"));

        // ---------- BULK UPDATE ----------
        if (request.getItems() != null && !request.getItems().isEmpty()) {

            var updated = request.getItems().stream().map(r -> {

                EventWorkItem item = workItemRepository.findById(r.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Work item not found: id=" + r.getId()
                        ));

                if (!item.getContractorEvent().getId().equals(request.getContractorEventId())) {
                    throw new ResourceNotFoundException(
                            "Item id=" + r.getId() + " does not belong to contractorEvent=" + request.getContractorEventId()
                    );
                }

                double total = calculateTotal(r);
                double remaining = total - r.getPaid();

                mapper.applyUpdate(item, r, total, remaining);
                workItemRepository.save(item);

                return mapper.toResponse(item);

            }).toList();

            totalHelper.recalculateTotals(ce);

            return ApiResponseUtil.updated(updated);
        }

        // ---------- SINGLE UPDATE ----------
        EventWorkItem item = workItemRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Work item not found"));

        if (!item.getContractorEvent().getId().equals(request.getContractorEventId())) {
            throw new ResourceNotFoundException("This item does not belong to the given contractor/event");
        }

        double total = calculateTotal(request);
        double remaining = total - request.getPaid();

        mapper.applyUpdate(item, request, total, remaining);
        workItemRepository.save(item);

        totalHelper.recalculateTotals(item.getContractorEvent());

        return ApiResponseUtil.updated(mapper.toResponse(item));
    }


    // ===================================================================
    // DELETE WORK ITEM
    // ===================================================================
    @Override
    public ApiResponse<?> deleteWorkItem(Long id) {

        EventWorkItem item = workItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Work item not found"));

        ContractorEvent ce = item.getContractorEvent();

        workItemRepository.delete(item);
        totalHelper.recalculateTotals(ce);

        return ApiResponseUtil.deleted();
    }


    // ===================================================================
    // GET BY ID
    // ===================================================================
    @Override
    public ApiResponse<?> getWorkItemById(Long id) {

        return workItemRepository.findById(id)
                .map(item -> ApiResponseUtil.fetched(mapper.toResponse(item)))
                .orElseThrow(() -> new ResourceNotFoundException("Work item not found"));
    }


    // ===================================================================
    // LIST ITEMS OF CONTRACTOR EVENT
    // ===================================================================
    @Override
    public ApiResponse<?> getAllWorkItems(Long contractorEventId, PageRequest pageRequest) {

        return queryResultHandler.fetchAndRespond(
                pageRequest,
                pageable -> workItemRepository.findByContractorEventId(contractorEventId, pageable),
                sort -> workItemRepository.findByContractorEventId(contractorEventId, sort),
                () -> workItemRepository.findByContractorEventId(contractorEventId),
                mapper::toResponse,
                "no work items found"
        );
    }


    // ===================================================================
    // TOTAL CALCULATOR
    // ===================================================================
    private double calculateTotal(EventWorkItemRequest r){

        if (r.getEntryType().name().equals("LABOUR")) {
            return r.getLabourCount() * r.getPerLabourWage();
        }

        return r.getQuantity() * r.getUnitCost();
    }
}
