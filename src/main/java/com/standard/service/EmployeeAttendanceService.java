package com.standard.service;

import com.standard.entity.dtos.employeeAttendance.AttendanceMarkRequest;
import com.standard.payload.ApiResponse;
import com.standard.entity.dtos.pagination.PageRequest;

import java.time.LocalDate;

public interface EmployeeAttendanceService {

    ApiResponse<?> markAttendance(AttendanceMarkRequest request, LocalDate date);
    ApiResponse<?> updateAttendance(AttendanceMarkRequest request, LocalDate date);

    ApiResponse<?> markAllPresent(LocalDate date);
    ApiResponse<?> markAllAbsent(LocalDate date);

    ApiResponse<?> deleteAttendance(Long id);
    ApiResponse<?> deleteAttendanceByDate(Long employeeId, LocalDate date);
    ApiResponse<?> deleteAllAttendanceByDate(LocalDate date);

    ApiResponse<?> getAttendanceByDate(LocalDate date, PageRequest pageRequest);

    ApiResponse<?> getMonthlySummary(Long employeeId, int year, int month);
    ApiResponse<?> getMonthlySummaryForAllEmployees(int year, int month, PageRequest pageRequest);
}
