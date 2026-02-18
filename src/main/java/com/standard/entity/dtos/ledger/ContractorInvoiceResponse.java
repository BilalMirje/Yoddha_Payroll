package com.standard.entity.dtos.ledger;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorInvoiceResponse {

    private String eventName;
    private LocalDate eventDate;
    private String contractorName;

    private String aadhar;
    private String contact;

    private Double totalAmount;
    private Double paid;
    private Double remaining;

    // NEW FIELDS
    private Double totalAdvance;
    private Double netRemaining;

    private List<EventWorkItemInvoiceResponse> items;
}