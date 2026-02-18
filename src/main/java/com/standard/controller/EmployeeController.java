package com.standard.controller;


import com.standard.entity.dtos.employeeDtos.EmployeeRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.payload.ApiResponse;
import com.standard.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/create-employee")
    public ResponseEntity<?> createEmployee(
            @Valid @ModelAttribute EmployeeRequest request,
            @RequestParam(required = false) MultipartFile profilePhoto
    ) {

        ApiResponse<?> apiResponse = employeeService.create(request, profilePhoto);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update-employee-by-id")
    public ResponseEntity<?> updateEmployee(
            @RequestParam Long id,
            @ModelAttribute EmployeeRequest request,
            @RequestParam(required = false) MultipartFile profilePhoto
    ) {
        ApiResponse<?> update = employeeService.update(id, request, profilePhoto);
        return ResponseEntity.ok(update);
    }

    @GetMapping("/get-all-employees")
    public ResponseEntity<?> getAllEmployees(
       PageRequest pageRequest
    ) {
        ApiResponse<?> all = employeeService.getAll(pageRequest);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/get-employee-by-id")
    public ResponseEntity<?> getEmployeeById(@RequestParam Long id)
    {
        ApiResponse<?> byId = employeeService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @DeleteMapping("/delete-employee-by-id")
    public ResponseEntity<?> deleteEmployee(@RequestParam Long id) {
        ApiResponse<?> delete = employeeService.delete(id);
        return ResponseEntity.ok(delete);
    }
}
