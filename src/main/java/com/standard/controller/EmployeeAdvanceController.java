package com.standard.controller;

import com.standard.entity.dtos.employeeAdvance.EmployeeAdvanceRequest;
import com.standard.payload.ApiResponse;
import com.standard.service.EmployeeAdvanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.standard.entity.dtos.pagination.PageRequest;


@RestController
@RequestMapping("/api/employee-advance")
@RequiredArgsConstructor
public class EmployeeAdvanceController {

    private final EmployeeAdvanceService advanceService;

    @PostMapping("/create-advance")
    public ResponseEntity<?> create(
            @RequestBody EmployeeAdvanceRequest request
    ){
        ApiResponse<?> response = advanceService.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-advance")
    public ResponseEntity<?> update(
            @RequestParam Long id,
            @RequestBody EmployeeAdvanceRequest request
    ){
        ApiResponse<?> response = advanceService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-advance-by-id")
    public ResponseEntity<?> getById(@RequestParam Long id){
        return ResponseEntity.ok(advanceService.getById(id));
    }

    @GetMapping("/get-all-advance")
    public ResponseEntity<?> getAll(
            PageRequest pageRequest
    ){
        return ResponseEntity.ok(
                advanceService.getAll(pageRequest)
        );
    }

    @DeleteMapping("/delete-advance-by-id")
    public ResponseEntity<?> delete(@RequestParam Long id){
        return ResponseEntity.ok(advanceService.delete(id));
    }
}
