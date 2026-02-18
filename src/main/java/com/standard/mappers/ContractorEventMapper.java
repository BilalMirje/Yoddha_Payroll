package com.standard.mappers;

import com.standard.entity.Contractor;
import com.standard.entity.ContractorEvent;
import com.standard.entity.Event;
import com.standard.entity.dtos.ledger.ContractorEventLedgerResponse;
import com.standard.entity.dtos.eventWorkItem.ContractorEventSummaryResponse;
import com.standard.entity.dtos.ledger.ContractorLedgerSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class ContractorEventMapper {

    public ContractorEventSummaryResponse toSummary(ContractorEvent ce){

        return ContractorEventSummaryResponse.builder()
                .contractorEventId(ce.getId())
                .contractorId(ce.getContractor().getId())
                .contractorName(ce.getContractor().getName())
                .totalAmount(ce.getTotalAmount())
                .totalPaid(ce.getTotalPaid())
                .totalRemaining(ce.getTotalRemaining())
                .build();
    }

    public ContractorEventLedgerResponse toLedgerResponse(
            ContractorEvent ce,
            Double totalAdvance,
            double netRemaining
    ){
        return ContractorEventLedgerResponse.builder()
                .contractorEventId(ce.getId())
                .contractorId(ce.getContractor().getId())
                .contractorName(ce.getContractor().getName())
                .eventName(ce.getEvent().getEventName())
                .totalAmount(ce.getTotalAmount())
                .totalAdvance(totalAdvance)
                .totalPaid(ce.getTotalPaid())
                .netRemaining(netRemaining)
                .build();
    }

    public ContractorLedgerSummaryResponse toContractorLedgerSummary(
            Contractor contractor,
            double totalAmount,
            double totalAdvance,
            double totalPaid,
            double netPayable
    ){
        return ContractorLedgerSummaryResponse.builder()
                .contractorId(contractor.getId())
                .contractorName(contractor.getName())
                .totalAmount(totalAmount)
                .totalAdvance(totalAdvance)
                .totalPaid(totalPaid)
                .netPayable(netPayable)
                .build();
    }

    public ContractorEvent createEntity(Event event, Contractor contractor){

        return ContractorEvent.builder()
                .event(event)
                .contractor(contractor)
                .totalAmount(0.0)
                .totalPaid(0.0)
                .totalRemaining(0.0)
                .build();
    }
}
