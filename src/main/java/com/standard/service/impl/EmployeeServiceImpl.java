package com.standard.service.impl;

import com.standard.entity.Employee;

import com.standard.entity.dtos.employeeDtos.EmployeeRequest;
import com.standard.exceptions.handlers.DuplicateRecordException;
import com.standard.exceptions.handlers.ResourceNotFoundException;
import com.standard.mappers.EmployeeMapper;
import com.standard.payload.ApiResponse;
import com.standard.payload.ApiResponseUtil;
import com.standard.queryHelper.QueryResultHandler;
import com.standard.repository.EmployeeRepository;
import com.standard.service.EmployeeService;
import com.standard.uploads.S3ImageUpload;
import lombok.RequiredArgsConstructor;
import com.standard.entity.dtos.pagination.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper mapper;
    private final S3ImageUpload s3ImageUpload;
    private final QueryResultHandler queryResultHandler;

    @Override
    public ApiResponse<?> create(EmployeeRequest request, MultipartFile profilePhoto) {

        if (employeeRepository.existsByAadharNumber(request.getAadharNumber())) {
            throw new DuplicateRecordException("Aadhar already registered");
        }


        Employee employee = mapper.toEntity(request);

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String url = s3ImageUpload.uploadImageToS3(profilePhoto);
            if (url != null) employee.setProfilePhoto(url);
        }

        employeeRepository.save(employee);
        return ApiResponseUtil.created(mapper.toResponse(employee));
    }

    @Override
    public ApiResponse<?> update(Long id, EmployeeRequest request, MultipartFile profilePhoto) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Duplicate Aadhar check (ignore same record)
        if (!employee.getAadharNumber().equals(request.getAadharNumber())
                && employeeRepository.existsByAadharNumber(request.getAadharNumber())) {

            return ApiResponseUtil.duplicateRecord(
                    "duplicate",
                    "Aadhar already registered with another employee",
                    null
            );
        }

        // Update fields
        mapper.updateEntity(employee, request);

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String url = s3ImageUpload.uploadImageToS3(profilePhoto);
            if (url != null) {
                employee.setProfilePhoto(url);
            }
        }

        employeeRepository.save(employee);

        return ApiResponseUtil.updated(mapper.toResponse(employee));
    }


    @Override
    public ApiResponse<?> getById(Long id) {
        return employeeRepository.findById(id)
                .map(emp -> ApiResponseUtil.fetched(mapper.toResponse(emp)))
                .orElse(
                        ApiResponseUtil.notFound(
                                "not_found",
                                "Employee not found",
                                null
                        )
               );
    }

    @Override
    public ApiResponse<?> delete(Long id) {
        if (!employeeRepository.existsById(id)) throw new ResourceNotFoundException("Employee not found with id "+id);
        employeeRepository.deleteById(id);
        return ApiResponseUtil.deleted();
    }



    @Override
    public ApiResponse<?> getAll(PageRequest pageRequest) {
        return queryResultHandler.fetchAndRespond(
                pageRequest,
                pageable -> employeeRepository.findAll(pageable),
                sort -> employeeRepository.findAll(sort),
                () -> employeeRepository.findAll(),
                mapper::toResponse,
                "no record found"
        );
    }

}
