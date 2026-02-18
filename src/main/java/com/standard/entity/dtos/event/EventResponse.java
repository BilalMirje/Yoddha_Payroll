package com.standard.entity.dtos.event;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {

    private Long id;
    private String eventName;
    private String location;
    private LocalDate eventDate;
}