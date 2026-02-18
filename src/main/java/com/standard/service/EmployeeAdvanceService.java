package com.standard.service;

import com.standard.entity.dtos.employeeAdvance.EmployeeAdvanceRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.payload.ApiResponse;


public interface EmployeeAdvanceService {

    ApiResponse<?> create(EmployeeAdvanceRequest request);

    ApiResponse<?> update(Long id, EmployeeAdvanceRequest request);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> getAll(PageRequest pageRequest);

    ApiResponse<?> delete(Long id);
}
