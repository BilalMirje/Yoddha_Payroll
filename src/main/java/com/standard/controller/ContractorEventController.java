package com.standard.controller;

import com.standard.entity.dtos.eventWorkItem.ContractorEventRequest;
import com.standard.entity.dtos.eventWorkItem.ContractorPaymentRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.payload.ApiResponse;
import com.standard.service.ContractorEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contractor-events")
@RequiredArgsConstructor
public class ContractorEventController {

    private final ContractorEventService contractorEventService;

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<?>> assign(@RequestBody ContractorEventRequest request){
        return ResponseEntity.ok(contractorEventService.assignContractor(request));
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ApiResponse<?>> getById(@RequestParam Long id){
        return ResponseEntity.ok(contractorEventService.getById(id));
    }

    @GetMapping("/get-all-by-event")
    public ResponseEntity<ApiResponse<?>> getAllByEvent(
            @RequestParam Long eventId,
            PageRequest pageRequest
    ){
        return ResponseEntity.ok(
                contractorEventService.getAllByEvent(eventId, pageRequest)
        );
    }

    @GetMapping("/get-contractor-ledger/all-event")
    public ResponseEntity<ApiResponse<?>> contractorLedger(@RequestParam Long contractorId){
        return ResponseEntity.ok(contractorEventService.getContractorLedger(contractorId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> delete(@RequestParam Long id){
        return ResponseEntity.ok(contractorEventService.deleteContractorEvent(id));
    }

    @PostMapping("/pay-to-contractor")
    public ResponseEntity<ApiResponse<?>> pay(@RequestBody ContractorPaymentRequest request){
        return ResponseEntity.ok(contractorEventService.payToContractor(request));
    }

    @GetMapping("/payment-history/by-contractor")
    public ResponseEntity<ApiResponse<?>> getPaymentHistory(
            @RequestParam Long contractorId
    ){
        return ResponseEntity.ok(
                contractorEventService.getPaymentHistoryByContractor(contractorId)
        );
    }


}
