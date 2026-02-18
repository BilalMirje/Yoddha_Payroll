package com.standard.entity.dtos.contractorAdvance;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorAdvanceHistoryResponse {

    private Long contractorId;
    private String contractorName;

    private Double totalAdvanceGiven;

    private List<ContractorAdvanceRow> advances;
}
