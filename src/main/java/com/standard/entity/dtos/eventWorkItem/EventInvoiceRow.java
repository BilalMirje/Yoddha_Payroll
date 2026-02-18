package com.standard.entity.dtos.eventWorkItem;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventInvoiceRow {

    private String contractorName;

    private String description;

    private Integer labourCount;
    private Double perLabourWage;

    private Integer quantity;
    private Double unitCost;

    private Double totalAmount;
    private Double paid;
    private Double remaining;
}
