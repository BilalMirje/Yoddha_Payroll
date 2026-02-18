package com.standard.controller;

import com.standard.entity.dtos.employeeAllowance.EmployeeAllowanceRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.payload.ApiResponse;
import com.standard.service.EmployeeAllowanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee-allowance")
@RequiredArgsConstructor
public class EmployeeAllowanceController {

    private final EmployeeAllowanceService allowanceService;

    @PostMapping("/create-allowance")
    public ResponseEntity<?> create(@Validated @RequestBody EmployeeAllowanceRequest request){
        ApiResponse<?> res = allowanceService.create(request);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/update-allowance")
    public ResponseEntity<?> update(
            @RequestParam Long id,
            @Validated @RequestBody EmployeeAllowanceRequest request){
        ApiResponse<?> res = allowanceService.update(id, request);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/get-allowance-by-id")
    public ResponseEntity<?> getById(@RequestParam Long id){
        return ResponseEntity.ok(allowanceService.getById(id));
    }

    @GetMapping("/get-all-allowance")
    public ResponseEntity<?> getAll(PageRequest pageRequest){
        return ResponseEntity.ok(allowanceService.getAll(pageRequest));
    }

    @DeleteMapping("/delete-allowance-by-id")
    public ResponseEntity<?> delete(@RequestParam Long id){
        return ResponseEntity.ok(allowanceService.delete(id));
    }
}