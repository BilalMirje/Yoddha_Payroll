package com.standard.controller;

import com.standard.service.EmployeeReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class EmployeeReportController {

    private final EmployeeReportService reportService;

    @GetMapping("/employee-monthly-report")
    public ResponseEntity<?> employeeMonthlyReport(
            @RequestParam Long employeeId,
            @RequestParam int year,
            @RequestParam int month
    ){
        return ResponseEntity.ok(
                reportService.getMonthlyReport(employeeId, year, month)
        );
    }

    @GetMapping("/employee-monthly-report/all")
    public ResponseEntity<?> allEmployeesMonthlyReport(
            @RequestParam int year,
            @RequestParam int month,
            com.standard.entity.dtos.pagination.PageRequest pageRequest
    ){
        return ResponseEntity.ok(
                reportService.getMonthlyReportForAllEmployees(year, month, pageRequest)
        );
    }
}