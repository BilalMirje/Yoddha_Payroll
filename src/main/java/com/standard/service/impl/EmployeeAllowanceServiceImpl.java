package com.standard.service.impl;


import com.standard.entity.Employee;
import com.standard.entity.EmployeeAllowance;
import com.standard.entity.dtos.employeeAllowance.EmployeeAllowanceRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.exceptions.handlers.ResourceNotFoundException;
import com.standard.mappers.EmployeeAllowanceMapper;
import com.standard.payload.ApiResponse;
import com.standard.payload.ApiResponseUtil;
import com.standard.queryHelper.QueryResultHandler;
import com.standard.repository.EmployeeAllowanceRepository;
import com.standard.repository.EmployeeRepository;
import com.standard.service.EmployeeAllowanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeAllowanceServiceImpl implements EmployeeAllowanceService {

    private final EmployeeAllowanceRepository allowanceRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeAllowanceMapper mapper;
    private final QueryResultHandler queryResultHandler;

    @Override
    public ApiResponse<?> create(EmployeeAllowanceRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        EmployeeAllowance allowance = mapper.toEntity(employee, request);

        allowanceRepository.save(allowance);

        return ApiResponseUtil.created(mapper.toResponse(allowance));
    }

    @Override
    public ApiResponse<?> update(Long id, EmployeeAllowanceRequest request) {

        EmployeeAllowance allowance = allowanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allowance not found"));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        mapper.updateEntity(allowance, request, employee);

        return ApiResponseUtil.updated(mapper.toResponse(allowance));
    }

    @Override
    public ApiResponse<?> getById(Long id) {
        return allowanceRepository.findById(id)
                .map(a -> ApiResponseUtil.fetched(mapper.toResponse(a)))
                .orElseThrow(()->new ResourceNotFoundException("Allowance not found with id "+id));
    }

    @Override
    public ApiResponse<?> getAll(PageRequest pageRequest) {

        return queryResultHandler.fetchAndRespond(
                pageRequest,
                pageable -> allowanceRepository.findAll(pageable),
                sort -> allowanceRepository.findAll(sort),
                () -> allowanceRepository.findAll(),
                mapper::toResponse,
                "no record found"
        );
    }

    @Override
    public ApiResponse<?> delete(Long id) {

        EmployeeAllowance allowance = allowanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allowance not found"));

        allowanceRepository.delete(allowance);

        return ApiResponseUtil.deleted();
    }




}
