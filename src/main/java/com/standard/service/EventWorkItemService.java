package com.standard.service;

import com.standard.entity.dtos.eventWorkItem.EventWorkItemRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.payload.ApiResponse;

public interface EventWorkItemService {

    ApiResponse<?> addWorkItem(EventWorkItemRequest request);

    ApiResponse<?> updateWorkItem(EventWorkItemRequest request);

     ApiResponse<?> deleteWorkItem(Long id);

     ApiResponse<?> getWorkItemById(Long id);

     ApiResponse<?> getAllWorkItems(Long contractorEventId, PageRequest pageRequest);
}
