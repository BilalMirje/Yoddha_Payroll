package com.standard.controller;

import com.standard.entity.dtos.event.EventRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.payload.ApiResponse;
import com.standard.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> create(
            @Valid @RequestBody EventRequest request){
        return ResponseEntity.ok(eventService.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> update(
            @RequestParam Long id,
            @RequestBody EventRequest request){
        return ResponseEntity.ok(eventService.update(id, request));
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ApiResponse<?>> getById(@RequestParam Long id){
        return ResponseEntity.ok(eventService.getById(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAll(PageRequest pageRequest){
        return ResponseEntity.ok(eventService.getAll(pageRequest));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> delete(@RequestParam Long id){
        return ResponseEntity.ok(eventService.delete(id));
    }
}
