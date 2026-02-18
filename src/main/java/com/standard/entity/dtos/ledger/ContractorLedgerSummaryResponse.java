package com.standard.entity.dtos.ledger;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorLedgerSummaryResponse {

    private Long contractorId;
    private String contractorName;

    private Double totalAmount;      // All events total
    private Double totalAdvance;     // From contractor_advance
    private Double totalPaid;        // All payments made
    private Double netPayable;       // totalAmount - totalAdvance - totalPaid
}