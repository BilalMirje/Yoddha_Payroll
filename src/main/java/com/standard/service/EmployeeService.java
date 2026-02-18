package com.standard.service;

import com.standard.entity.dtos.employeeDtos.EmployeeRequest;
import com.standard.payload.ApiResponse;
import com.standard.entity.dtos.pagination.PageRequest;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {

    ApiResponse<?> create(EmployeeRequest request, MultipartFile profilePhoto);

    ApiResponse<?> update(Long id, EmployeeRequest request, MultipartFile profilePhoto);


    ApiResponse<?> getById(Long id);

    ApiResponse<?> delete(Long id);

    ApiResponse<?> getAll(PageRequest pageRequest);

}
