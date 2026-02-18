package com.standard.service.impl;

import com.standard.entity.Event;
import com.standard.entity.dtos.event.EventRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.exceptions.handlers.ResourceNotFoundException;
import com.standard.mappers.EventMapper;
import com.standard.payload.ApiResponse;
import com.standard.payload.ApiResponseUtil;
import com.standard.queryHelper.QueryResultHandler;
import com.standard.repository.EventRepository;
import com.standard.service.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper mapper;
    private final QueryResultHandler queryResultHandler;

    @Override
    public ApiResponse<?> create(EventRequest request) {

        Event event = mapper.toEntity(request);
        eventRepository.save(event);

        return ApiResponseUtil.created(mapper.toResponse(event));
    }

    @Override
    public ApiResponse<?> update(Long id, EventRequest request) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        mapper.updateEntity(event, request);

        eventRepository.save(event);

        return ApiResponseUtil.updated(mapper.toResponse(event));
    }

    @Override
    public ApiResponse<?> getById(Long id) {
        return eventRepository.findById(id)
                .map(e -> ApiResponseUtil.fetched(mapper.toResponse(e)))
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    @Override
    public ApiResponse<?> getAll(PageRequest pageRequest) {

        return queryResultHandler.fetchAndRespond(
                pageRequest,
                pageable -> eventRepository.findAll(pageable),
                sort -> eventRepository.findAll(sort),
                eventRepository::findAll,
                mapper::toResponse,
                "no events found"
        );
    }

    @Override
    public ApiResponse<?> delete(Long id) {

        if (!eventRepository.existsById(id))
            throw new ResourceNotFoundException("Event not found");

        eventRepository.deleteById(id);

        return ApiResponseUtil.deleted();
    }
}