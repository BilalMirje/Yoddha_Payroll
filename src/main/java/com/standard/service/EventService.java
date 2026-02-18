package com.standard.service;

import com.standard.entity.dtos.event.EventRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.payload.ApiResponse;

public interface EventService {

    ApiResponse<?> create(EventRequest request);

    ApiResponse<?> update(Long id, EventRequest request);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> getAll(PageRequest pageRequest);

    ApiResponse<?> delete(Long id);
}
