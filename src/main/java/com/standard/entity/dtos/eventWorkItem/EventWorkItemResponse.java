package com.standard.entity.dtos.eventWorkItem;

import com.standard.entity.dtos.ledger.ContractorEventLedgerRow;
import com.standard.entity.dtos.ledger.EventWorkItemInvoiceResponse;
import com.standard.util.enums.ShiftType;
import com.standard.util.enums.WorkItemType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventWorkItemResponse {

    private Long id;
    private Long contractorEventId;

    private String description;
    private WorkItemType entryType;
    private ShiftType shiftType;

    private Integer labourCount;
    private Double perLabourWage;

    private Integer quantity;
    private Double unitCost;

    private Double totalAmount;
    private Double paid;
    private Double remaining;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ContractorFullLedgerResponse {

        private Long contractorId;
        private String contractorName;
        private String aadhar;
        private String contact;
        private Double totalAmount;
        private Double totalAdvance;
        private Double totalPaid;
        private Double netPayable;

        private List<ContractorEventLedgerRow> events;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EventContractorInvoiceBlock {

        private String contractorName;

        private Double totalAmount;
        private Double paid;
        private Double remaining;

        private List<EventWorkItemInvoiceResponse> items;
    }
}
