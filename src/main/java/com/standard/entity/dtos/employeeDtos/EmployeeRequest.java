package com.standard.entity.dtos.employeeDtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Contact is required")
    private String contact;

    // optional
    private String email;

    @NotNull(message = "Salary is required")
    private Double salary;

    @NotNull(message = "In time is required")
    private LocalTime inTime;

    @NotNull(message = "Out time is required")
    private LocalTime outTime;

    @NotBlank(message = "Aadhar number is required")
    private String aadharNumber;


    @NotNull(message = "Date of joining is required")
    private LocalDate dateOfJoining;
}
