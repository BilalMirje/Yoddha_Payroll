package com.standard.controller;

import com.standard.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/event")
    public ResponseEntity<?> eventInvoice(@RequestParam Long eventId){
        return ResponseEntity.ok(invoiceService.generateEventInvoice(eventId));
    }

    @GetMapping("/contractor")
    public ResponseEntity<?> contractorInvoice(@RequestParam Long contractorEventId){
        return ResponseEntity.ok(invoiceService.generateContractorInvoice(contractorEventId));
    }
}
