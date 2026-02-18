package com.standard.entity.dtos.ledger;

import com.standard.entity.dtos.eventWorkItem.EventWorkItemResponse;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventInvoiceResponse {

    private String eventName;
    private String eventLocation;
    private LocalDate eventDate;

    private List<EventWorkItemResponse.EventContractorInvoiceBlock> contractors;

    private Double grandTotal;
    private Double grandPaid;
    private Double grandRemaining;
}
