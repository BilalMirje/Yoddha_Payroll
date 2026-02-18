package com.standard.service;

import com.standard.payload.ApiResponse;

public interface EmployeeReportService {

    ApiResponse<?> getMonthlyReport(Long employeeId, int year, int month);

    ApiResponse<?> getMonthlyReportForAllEmployees(int year, int month,
                                                   com.standard.entity.dtos.pagination.PageRequest pageRequest);
}
