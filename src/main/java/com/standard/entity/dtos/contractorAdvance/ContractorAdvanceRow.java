package com.standard.entity.dtos.contractorAdvance;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorAdvanceRow {

    private Long advanceId;

    private Long contractorEventId;
    private String eventName;

    private Double amount;
    private LocalDate advanceDate;
    private String reason;
}
