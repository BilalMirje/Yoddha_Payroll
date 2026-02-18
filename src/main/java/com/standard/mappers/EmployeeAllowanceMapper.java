package com.standard.mappers;


import com.standard.entity.Employee;
import com.standard.entity.EmployeeAllowance;
import com.standard.entity.dtos.employeeAllowance.EmployeeAllowanceRequest;
import com.standard.entity.dtos.employeeAllowance.EmployeeAllowanceResponse;
import org.springframework.stereotype.Component;

@Component
public class EmployeeAllowanceMapper {

    public EmployeeAllowance toEntity(Employee employee, EmployeeAllowanceRequest dto) {
        return EmployeeAllowance.builder()
                .employee(employee)
                .amount(dto.getAmount())
                .allowanceDate(dto.getAllowanceDate())
                .reason(dto.getReason())
                .build();
    }

    public void updateEntity(EmployeeAllowance entity,
                             EmployeeAllowanceRequest dto,
                             Employee employee) {

        entity.setEmployee(employee);
        entity.setAmount(dto.getAmount());
        entity.setAllowanceDate(dto.getAllowanceDate());
        entity.setReason(dto.getReason());
    }

    public EmployeeAllowanceResponse toResponse(EmployeeAllowance entity) {
        return EmployeeAllowanceResponse.builder()
                .id(entity.getId())
                .employeeId(entity.getEmployee().getId())
                .employeeName(entity.getEmployee().getName())
                .amount(entity.getAmount())
                .allowanceDate(entity.getAllowanceDate())
                .reason(entity.getReason())
                .build();
    }
}
