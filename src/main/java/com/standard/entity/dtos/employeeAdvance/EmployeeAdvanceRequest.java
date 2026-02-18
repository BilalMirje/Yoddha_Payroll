package com.standard.entity.dtos.employeeAdvance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAdvanceRequest {

    @NotNull(message = "Employee is required")
    private Long employeeId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @NotNull(message = "Advance date is required")
    private LocalDate advanceDate;

    @NotBlank(message = "Reason is required")
    private String reason;
}
