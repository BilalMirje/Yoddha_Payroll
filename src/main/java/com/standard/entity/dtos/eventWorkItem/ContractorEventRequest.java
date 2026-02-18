package com.standard.entity.dtos.eventWorkItem;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorEventRequest {

    @NotNull(message = "Event id is required")
    private Long eventId;

    @NotNull(message = "Contractor id is required")
    private Long contractorId;

    private List<Long> contractorIds;
}
