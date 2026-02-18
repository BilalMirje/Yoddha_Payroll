package com.standard.mappers;

import com.standard.entity.ContractorAdvance;
import com.standard.entity.ContractorEvent;
import com.standard.entity.dtos.contractorAdvance.ContractorAdvanceRequest;
import com.standard.entity.dtos.contractorAdvance.ContractorAdvanceResponse;
import com.standard.entity.dtos.contractorAdvance.ContractorAdvanceRow;
import org.springframework.stereotype.Component;

@Component
public class ContractorAdvanceMapper {

    public ContractorAdvance toEntity(ContractorEvent ce,
                                      ContractorAdvanceRequest request){

        return ContractorAdvance.builder()
                .contractorEvent(ce)         //  CHANGE
                .amount(request.getAmount())
                .advanceDate(request.getAdvanceDate())
                .reason(request.getReason())
                .build();
    }

    public void updateEntity(ContractorAdvance advance,
                             ContractorAdvanceRequest request,
                             ContractorEvent ce){

        advance.setContractorEvent(ce);
        advance.setAmount(request.getAmount());
        advance.setAdvanceDate(request.getAdvanceDate());
        advance.setReason(request.getReason());
    }

    public ContractorAdvanceResponse toResponse(ContractorAdvance advance){

        return ContractorAdvanceResponse.builder()
                .id(advance.getId())
                .contractorEventId(
                        advance.getContractorEvent().getId()
                )
                .contractorName(
                        advance.getContractorEvent().getContractor().getName()
                )
                .eventName(
                        advance.getContractorEvent().getEvent().getEventName()
                )
                .amount(advance.getAmount())
                .advanceDate(advance.getAdvanceDate())
                .reason(advance.getReason())
                .build();
    }

    public ContractorAdvanceRow toHistoryRow(ContractorAdvance a){

        return ContractorAdvanceRow.builder()
                .advanceId(a.getId())
                .contractorEventId(a.getContractorEvent().getId())
                .eventName(a.getContractorEvent().getEvent().getEventName())
                .amount(a.getAmount())
                .advanceDate(a.getAdvanceDate())
                .reason(a.getReason())
                .build();
    }
}
