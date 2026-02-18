package com.standard.entity.dtos.employeeAllowance;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAllowanceResponse {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private Double amount;
    private LocalDate allowanceDate;
    private String reason;
}
