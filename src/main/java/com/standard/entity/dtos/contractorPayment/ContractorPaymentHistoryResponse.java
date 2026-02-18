package com.standard.entity.dtos.contractorPayment;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorPaymentHistoryResponse {

    private Long contractorId;
    private String contractorName;

    private Double totalPaid;

    private List<ContractorPaymentRow> payments;
}