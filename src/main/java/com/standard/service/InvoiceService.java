package com.standard.service;

import com.standard.payload.ApiResponse;

public interface InvoiceService {

    ApiResponse<?> generateEventInvoice(Long eventId);

    ApiResponse<?> generateContractorInvoice(Long contractorEventId);
}
