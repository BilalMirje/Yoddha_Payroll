package com.standard.service;

import com.standard.entity.dtos.contractor.ContractorRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.payload.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ContractorService {

    ApiResponse<?> create(ContractorRequest request, MultipartFile profilePhoto);

    ApiResponse<?> update(Long id, ContractorRequest request, MultipartFile profilePhoto);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> delete(Long id);

    ApiResponse<?> getAll(PageRequest pageRequest);
}