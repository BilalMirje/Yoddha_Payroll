package com.standard.controller;

import com.standard.entity.dtos.contractor.ContractorRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.service.ContractorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/contractors")
@RequiredArgsConstructor
public class ContractorController {

    private final ContractorService contractorService;

    @PostMapping("/create-contractor")
    public ResponseEntity<?> create(
            @Valid @ModelAttribute ContractorRequest request,
            @RequestParam(required = false) MultipartFile profilePhoto
    ){
        return ResponseEntity.ok(contractorService.create(request, profilePhoto));
    }

    @PutMapping("/update-contractor")
    public ResponseEntity<?> update(
            @RequestParam Long id,
            @ModelAttribute ContractorRequest request,
            @RequestParam(required = false) MultipartFile profilePhoto
    ){
        return ResponseEntity.ok(contractorService.update(id, request, profilePhoto));
    }

    @GetMapping("/get-contractor-by-id")
    public ResponseEntity<?> getById(@RequestParam Long id){
        return ResponseEntity.ok(contractorService.getById(id));
    }

    @GetMapping("/get-all-contractor")
    public ResponseEntity<?> getAll(PageRequest pageRequest){
        return ResponseEntity.ok(contractorService.getAll(pageRequest));
    }

    @DeleteMapping("/delete-contractor-by-id")
    public ResponseEntity<?> delete(@RequestParam Long id){
        return ResponseEntity.ok(contractorService.delete(id));
    }
}
