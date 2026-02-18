package com.standard.mappers;

import com.standard.entity.Employee;
import com.standard.entity.dtos.employeeReportDtos.EmployeeMonthlyReportResponse;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Component
public class EmployeeReportMapper {

    public EmployeeMonthlyReportResponse toMonthlyReportResponse(
            Employee employee,
            YearMonth month,
            int totalDays,
            int presentDays,
            int absentDays,
            double perDaySalary,
            double attendanceSalary,
            double totalAllowance,
            double totalAdvance,
            double netPayable
    ){
        return EmployeeMonthlyReportResponse.builder()
                .employeeId(employee.getId())
                .employeeName(employee.getName())
                .month(month)
                .monthlySalary(employee.getSalary())
                .totalMonthDays(totalDays)
                .presentDays(presentDays)
                .absentDays(absentDays)
                .perDaySalary(perDaySalary)
                .attendanceSalary(attendanceSalary)
                .totalAllowance(totalAllowance)
                .totalAdvance(totalAdvance)
                .netPayableAmount(netPayable)
                .build();
    }

}
