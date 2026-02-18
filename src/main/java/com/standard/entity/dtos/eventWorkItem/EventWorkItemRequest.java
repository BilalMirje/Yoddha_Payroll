package com.standard.entity.dtos.eventWorkItem;

import com.standard.util.enums.ShiftType;
import com.standard.util.enums.WorkItemType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventWorkItemRequest {

    private Long contractorEventId;

    private String description;

    private WorkItemType entryType;

    private ShiftType shiftType;

    private Integer labourCount;
    private Double perLabourWage;

    private Integer quantity;
    private Double unitCost;

    private Double paid;

    private Long id;   // < existing item id (single update)

    // ---------- FOR BULK MODE ----------
    private List<EventWorkItemRequest> items;  // < child items list
}