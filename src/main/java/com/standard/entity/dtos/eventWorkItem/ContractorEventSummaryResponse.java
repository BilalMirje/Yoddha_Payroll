package com.standard.entity.dtos.eventWorkItem;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorEventSummaryResponse {

    private Long contractorEventId;
    private Long contractorId;
    private String contractorName;

    private Double totalAmount;
    private Double totalPaid;
    private Double totalRemaining;
}
