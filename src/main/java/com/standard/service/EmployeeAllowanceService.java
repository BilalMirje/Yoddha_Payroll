package com.standard.service;

import com.standard.entity.dtos.employeeAllowance.EmployeeAllowanceRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.payload.ApiResponse;

public interface EmployeeAllowanceService {

    ApiResponse<?> create(EmployeeAllowanceRequest request);

    ApiResponse<?> update(Long id, EmployeeAllowanceRequest request);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> getAll(PageRequest pageRequest);

    ApiResponse<?> delete(Long id);
}
