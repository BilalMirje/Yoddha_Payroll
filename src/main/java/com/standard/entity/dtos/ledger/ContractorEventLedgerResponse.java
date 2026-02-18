package com.standard.entity.dtos.ledger;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorEventLedgerResponse {

    private Long contractorEventId;
    private Long contractorId;
    private String contractorName;
    private String eventName;

    private Double totalAmount;
    private Double totalAdvance;
    private Double totalPaid;
    private Double netRemaining;     // totalAmount - advance - paid
}
