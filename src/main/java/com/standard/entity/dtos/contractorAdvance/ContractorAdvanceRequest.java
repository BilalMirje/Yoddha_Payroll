package com.standard.entity.dtos.contractorAdvance;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorAdvanceRequest {

    @NotNull(message = "Contractor Event is required")
    private Long contractorEventId;   //  CHANGE



    @NotNull(message = "Amount is required")
    private Double amount;

    @NotNull(message = "Advance date is required")
    private LocalDate advanceDate;

    @NotNull(message = "Reason is required")
    private String reason;
}