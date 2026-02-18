package com.standard.service;

import com.standard.entity.dtos.contractorAdvance.ContractorAdvanceRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.payload.ApiResponse;

public interface ContractorAdvanceService {

    ApiResponse<?> create(ContractorAdvanceRequest request);

    ApiResponse<?> update(Long id, ContractorAdvanceRequest request);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> getAll(PageRequest pageRequest);

    ApiResponse<?> delete(Long id);

    ApiResponse<?> getAdvanceHistoryByContractor(Long contractorId);

}