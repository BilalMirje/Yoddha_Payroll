package com.standard.mappers;

import com.standard.entity.Event;
import com.standard.entity.dtos.event.EventRequest;
import com.standard.entity.dtos.event.EventResponse;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event toEntity(EventRequest r){
        return Event.builder()
                .eventName(r.getEventName())
                .location(r.getLocation())
                .eventDate(r.getEventDate())
                .build();
    }

    public void updateEntity(Event e, EventRequest r){
        e.setEventName(r.getEventName());
        e.setLocation(r.getLocation());
        e.setEventDate(r.getEventDate());
    }

    public EventResponse toResponse(Event e){
        return EventResponse.builder()
                .id(e.getId())
                .eventName(e.getEventName())
                .location(e.getLocation())
                .eventDate(e.getEventDate())
                .build();
    }
}
