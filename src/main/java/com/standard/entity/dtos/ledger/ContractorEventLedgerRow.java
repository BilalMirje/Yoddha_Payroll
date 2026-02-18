package com.standard.entity.dtos.ledger;


import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ContractorEventLedgerRow {

    private Long contractorEventId;
    private Long eventId;
    private String eventName;
    private String eventLocation;

    private LocalDate eventDate;



    private Double totalAmount;
    private Double totalAdvance;
    private Double totalPaid;
    private Double netRemaining;
}

