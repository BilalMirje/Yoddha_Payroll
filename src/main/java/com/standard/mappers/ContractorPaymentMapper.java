package com.standard.mappers;


import com.standard.entity.ContractorPayment;
import com.standard.entity.dtos.contractorPayment.ContractorPaymentRow;
import org.springframework.stereotype.Component;

@Component
public class ContractorPaymentMapper {

    public ContractorPaymentRow toRow(ContractorPayment p){
        return ContractorPaymentRow.builder()
                .paymentId(p.getId())
                .contractorEventId(p.getContractorEvent().getId())
                .eventName(p.getContractorEvent().getEvent().getEventName())
                .amount(p.getAmount())
                .paymentDate(p.getPaymentDate())
                .remark(p.getRemark())
                .build();
    }
}