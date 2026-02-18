package com.standard.entity.dtos.eventWorkItem;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventWorkItemBulkRequest {
    private List<EventWorkItemRequest> items;
}