package com.standard.mappers;

import com.standard.entity.ContractorEvent;
import com.standard.entity.EventWorkItem;
import com.standard.entity.dtos.eventWorkItem.EventWorkItemRequest;
import com.standard.entity.dtos.eventWorkItem.EventWorkItemResponse;
import org.springframework.stereotype.Component;

@Component
public class EventWorkItemMapper {

    // ---------- ENTITY → RESPONSE ----------
    public EventWorkItemResponse toResponse(EventWorkItem w){

        return EventWorkItemResponse.builder()
                .id(w.getId())
                .contractorEventId(w.getContractorEvent().getId())
                .description(w.getDescription())
                .entryType(w.getEntryType())
                .shiftType(w.getShiftType())
                .labourCount(w.getLabourCount())
                .perLabourWage(w.getPerLabourWage())
                .quantity(w.getQuantity())
                .unitCost(w.getUnitCost())
                .totalAmount(w.getTotalAmount())
                .paid(w.getPaid())
                .remaining(w.getRemaining())
                .build();
    }

    // ---------- REQUEST → NEW ENTITY (CREATE) ----------
    public EventWorkItem toEntity(EventWorkItemRequest r,
                                  ContractorEvent ce,
                                  double total,
                                  double remaining){

        return EventWorkItem.builder()
                .contractorEvent(ce)
                .description(r.getDescription())
                .entryType(r.getEntryType())
                .shiftType(r.getShiftType())
                .labourCount(r.getLabourCount())
                .perLabourWage(r.getPerLabourWage())
                .quantity(r.getQuantity())
                .unitCost(r.getUnitCost())
                .totalAmount(total)
                .paid(r.getPaid())
                .remaining(remaining)
                .build();
    }

    // ---------- UPDATE EXISTING ENTITY ----------
    public void applyUpdate(EventWorkItem item,
                            EventWorkItemRequest r,
                            double total,
                            double remaining){

        item.setDescription(r.getDescription());
        item.setEntryType(r.getEntryType());
        item.setShiftType(r.getShiftType());
        item.setLabourCount(r.getLabourCount());
        item.setPerLabourWage(r.getPerLabourWage());
        item.setQuantity(r.getQuantity());
        item.setUnitCost(r.getUnitCost());
        item.setTotalAmount(total);
        item.setPaid(r.getPaid());
        item.setRemaining(remaining);
    }
}
