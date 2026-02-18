package com.standard.entity.dtos.ledger;

import com.standard.util.enums.ShiftType;
import com.standard.util.enums.WorkItemType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventWorkItemInvoiceResponse {

    private Long id;
    private String description;
    private WorkItemType  entryType;
    private ShiftType shiftType;

    private Integer labourCount;
    private Double perLabourWage;

    private Integer quantity;
    private Double unitCost;

    private Double totalAmount;
    private Double paid;
    private Double remaining;
}