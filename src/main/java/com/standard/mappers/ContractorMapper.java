package com.standard.mappers;


import com.standard.entity.Contractor;
import com.standard.entity.dtos.contractor.ContractorRequest;
import com.standard.entity.dtos.contractor.ContractorResponse;
import org.springframework.stereotype.Component;

@Component
public class ContractorMapper {

    public Contractor toEntity(ContractorRequest request){
        return Contractor.builder()
                .name(request.getName())
                .contact(request.getContact())
                .aadharNumber(request.getAadharNumber())
                .build();
    }

    public void updateEntity(Contractor contractor, ContractorRequest request){
        contractor.setName(request.getName());
        contractor.setContact(request.getContact());
        contractor.setAadharNumber(request.getAadharNumber());
    }

    public ContractorResponse toResponse(Contractor contractor){
        return ContractorResponse.builder()
                .id(contractor.getId())
                .name(contractor.getName())
                .contact(contractor.getContact())
                .aadharNumber(contractor.getAadharNumber())
                .profilePhoto(contractor.getProfilePhoto())
                .build();
    }
}