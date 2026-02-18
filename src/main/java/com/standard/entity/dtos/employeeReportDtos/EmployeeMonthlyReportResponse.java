package com.standard.entity.dtos.employeeReportDtos;

import lombok.*;

import java.time.YearMonth;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeMonthlyReportResponse {

    private Long employeeId;
    private String employeeName;

    private YearMonth month;

    private double monthlySalary;
    private int totalMonthDays;

    private int presentDays;
    private int absentDays;

    private double perDaySalary;
    private double attendanceSalary;

    private double totalAllowance;
    private double totalAdvance;

    private double netPayableAmount;
}