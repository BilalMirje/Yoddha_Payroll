package com.standard.entity.dtos.eventWorkItem;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorPaymentRequest {

    private Long contractorEventId;
    private Double amount;
    private LocalDate paymentDate;
    private String remark;

}