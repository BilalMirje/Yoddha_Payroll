package com.standard.entity.dtos.employeeAdvance;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAdvanceResponse {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private Double amount;
    private LocalDate advanceDate;
    private String reason;
}