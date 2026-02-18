package com.standard.service;

import com.standard.entity.dtos.eventWorkItem.ContractorEventRequest;
import com.standard.entity.dtos.eventWorkItem.ContractorPaymentRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.payload.ApiResponse;

public interface ContractorEventService {
    ApiResponse<?> assignContractor(ContractorEventRequest request);

    ApiResponse<?> getById(Long contractorEventId);

    ApiResponse<?> getAllByEvent(Long eventId, PageRequest pageRequest);

    ApiResponse<?> deleteContractorEvent(Long contractorEventId);
    ApiResponse<?> getContractorLedger(Long contractorId);

     ApiResponse<?> payToContractor(ContractorPaymentRequest request);
    ApiResponse<?> getPaymentHistoryByContractor(Long contractorId);


    ApiResponse<?> updateAssignedContractor(Long contractorEventId, ContractorEventRequest request);
}
