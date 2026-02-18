package com.standard.controller;

import com.standard.entity.dtos.contractorAdvance.ContractorAdvanceRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.service.ContractorAdvanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contractor-advance")
@RequiredArgsConstructor
public class ContractorAdvanceController {

    private final ContractorAdvanceService advanceService;

    @PostMapping("/create-advance")
    public ResponseEntity<?> create(@RequestBody ContractorAdvanceRequest request){
        return ResponseEntity.ok(advanceService.create(request));
    }

    @PutMapping("/update-advance")
    public ResponseEntity<?> update(
            @RequestParam Long id,
            @RequestBody ContractorAdvanceRequest request){
        return ResponseEntity.ok(advanceService.update(id, request));
    }

    @GetMapping("/get-advance-by-id")
    public ResponseEntity<?> getById(@RequestParam Long id){
        return ResponseEntity.ok(advanceService.getById(id));
    }

    @GetMapping("/get-all-advance")
    public ResponseEntity<?> getAll(PageRequest pageRequest){
        return ResponseEntity.ok(advanceService.getAll(pageRequest));
    }

    @DeleteMapping("/delete-advance-by-id")
    public ResponseEntity<?> delete(@RequestParam Long id){
        return ResponseEntity.ok(advanceService.delete(id));
    }

    @GetMapping("/history/by-contractor")
    public ResponseEntity<?> historyByContractor(@RequestParam Long contractorId){
        return ResponseEntity.ok(
                advanceService.getAdvanceHistoryByContractor(contractorId)
        );
    }
}
