package com.standard.entity.dtos.contractorPayment;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorPaymentRow {

    private Long paymentId;
    private Long contractorEventId;
    private String eventName;

    private Double amount;
    private LocalDate paymentDate;
    private String remark;
}