package com.standard.mappers;

import com.standard.entity.Contractor;
import com.standard.entity.ContractorEvent;
import com.standard.entity.dtos.eventWorkItem.EventWorkItemResponse;
import com.standard.entity.dtos.ledger.ContractorEventLedgerRow;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContractorEventLedgerMapper {

    public ContractorEventLedgerRow toLedgerRow(
            ContractorEvent ce,
            Double advance,
            double netRemaining
    ) {
        return ContractorEventLedgerRow.builder()
                .contractorEventId(ce.getId())
                .eventId(ce.getEvent().getId())
                .eventName(ce.getEvent().getEventName())
                .eventLocation(ce.getEvent().getLocation())
                .eventDate(ce.getEvent().getEventDate())
                .totalAmount(ce.getTotalAmount())
                .totalAdvance(advance)
                .totalPaid(ce.getTotalPaid())
                .netRemaining(netRemaining)
                .build();
    }


    public EventWorkItemResponse.ContractorFullLedgerResponse toFullLedgerResponse(
            Contractor contractor,
            List<ContractorEventLedgerRow> rows
    ) {

        double totalAmount = rows.stream().mapToDouble(ContractorEventLedgerRow::getTotalAmount).sum();
        double totalAdvance = rows.stream().mapToDouble(ContractorEventLedgerRow::getTotalAdvance).sum();
        double totalPaid = rows.stream().mapToDouble(ContractorEventLedgerRow::getTotalPaid).sum();
        double netPayable = rows.stream().mapToDouble(ContractorEventLedgerRow::getNetRemaining).sum();

        return EventWorkItemResponse.ContractorFullLedgerResponse.builder()
                .contractorId(contractor.getId())
                .contractorName(contractor.getName())
                .contact(contractor.getContact())
                .aadhar(contractor.getAadharNumber())
                .totalAmount(totalAmount)
                .totalAdvance(totalAdvance)
                .totalPaid(totalPaid)
                .netPayable(netPayable)
                .events(rows)
                .build();
    }
}
