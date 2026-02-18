package com.standard.entity.dtos.contractor;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Contact is required")
    private String contact;

    @NotBlank(message = "Aadhar number is required")
    private String aadharNumber;
}