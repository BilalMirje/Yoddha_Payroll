package com.standard.service.impl;

import com.standard.entity.Employee;
import com.standard.entity.EmployeeAdvance;
import com.standard.entity.dtos.employeeAdvance.EmployeeAdvanceRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.exceptions.handlers.ResourceNotFoundException;
import com.standard.mappers.EmployeeAdvanceMapper;
import com.standard.payload.ApiResponse;
import com.standard.payload.ApiResponseUtil;
import com.standard.queryHelper.QueryResultHandler;
import com.standard.repository.EmployeeAdvanceRepository;
import com.standard.repository.EmployeeRepository;
import com.standard.service.EmployeeAdvanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeAdvanceServiceImpl implements EmployeeAdvanceService {

    private final EmployeeAdvanceRepository advanceRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeAdvanceMapper mapper;
    private final QueryResultHandler queryResultHandler;


    @Override
    public ApiResponse<?> create(EmployeeAdvanceRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        EmployeeAdvance advance = mapper.toEntity(employee, request);

        advanceRepository.save(advance);

        return ApiResponseUtil.created(mapper.toResponse(advance));
    }

    @Override
    public ApiResponse<?> update(Long id, EmployeeAdvanceRequest request) {

        EmployeeAdvance advance = advanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Advance record not found"));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        mapper.updateEntity(advance, request, employee);

        return ApiResponseUtil.updated(mapper.toResponse(advance));
    }

    @Override
    public ApiResponse<?> getById(Long id) {
        return advanceRepository.findById(id)
                .map(a -> ApiResponseUtil.fetched(mapper.toResponse(a)))
                .orElseThrow(() -> new ResourceNotFoundException("Advance not found"));
    }

    @Override
    public ApiResponse<?> getAll(PageRequest pageRequest) {
        return queryResultHandler.fetchAndRespond(
                pageRequest,
                pageable -> advanceRepository.findAll(pageable),
                sort -> advanceRepository.findAll(sort),
                () -> advanceRepository.findAll(),
                mapper::toResponse,
                "no record found"
        );
    }


    @Override
    public ApiResponse<?> delete(Long id) {
        EmployeeAdvance advance = advanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("advance not found"));

        advanceRepository.delete(advance);

        return ApiResponseUtil.deleted();
    }
}