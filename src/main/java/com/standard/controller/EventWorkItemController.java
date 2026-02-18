package com.standard.controller;

import com.standard.entity.dtos.eventWorkItem.EventWorkItemRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.payload.ApiResponse;
import com.standard.service.EventWorkItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event-work-items")
@RequiredArgsConstructor
public class EventWorkItemController {

    private final EventWorkItemService workItemService;


    // ---------- ADD (single + bulk) ----------
    @PostMapping("/add-item")
    public ResponseEntity<ApiResponse<?>> add(
            @RequestBody EventWorkItemRequest request
    ){
        return ResponseEntity.ok(workItemService.addWorkItem(request));
    }

    // ---------- UPDATE (single + bulk) ----------
    @PutMapping("/update-item")
    public ResponseEntity<ApiResponse<?>> update(
            @RequestBody EventWorkItemRequest request
    ){
        return ResponseEntity.ok(workItemService.updateWorkItem(request));
    }
    @GetMapping("/get-item-by-id")
    public ResponseEntity<ApiResponse<?>> getById(@RequestParam Long id){
        return ResponseEntity.ok(workItemService.getWorkItemById(id));
    }

    @GetMapping("/get-all-items-by-contractorEvent-id")
    public ResponseEntity<ApiResponse<?>> getAllByContractorEvent(
            @RequestParam Long contractorEventId,
            PageRequest pageRequest
    ){
        return ResponseEntity.ok(
                workItemService.getAllWorkItems(contractorEventId, pageRequest)
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> delete(@RequestParam Long id){
        return ResponseEntity.ok(workItemService.deleteWorkItem(id));
    }
}
