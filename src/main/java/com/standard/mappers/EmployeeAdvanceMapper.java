package com.standard.mappers;


import com.standard.entity.Employee;
import com.standard.entity.EmployeeAdvance;
import com.standard.entity.dtos.employeeAdvance.EmployeeAdvanceRequest;
import com.standard.entity.dtos.employeeAdvance.EmployeeAdvanceResponse;
import org.springframework.stereotype.Component;

@Component
public class EmployeeAdvanceMapper {

    public EmployeeAdvance toEntity(Employee employee, EmployeeAdvanceRequest dto) {
        return EmployeeAdvance.builder()
                .employee(employee)
                .amount(dto.getAmount())
                .advanceDate(dto.getAdvanceDate())
                .reason(dto.getReason())
                .build();
    }

    public void updateEntity(EmployeeAdvance entity, EmployeeAdvanceRequest dto, Employee employee) {
        entity.setEmployee(employee);
        entity.setAmount(dto.getAmount());
        entity.setAdvanceDate(dto.getAdvanceDate());
        entity.setReason(dto.getReason());
    }

    public EmployeeAdvanceResponse toResponse(EmployeeAdvance entity) {
        return EmployeeAdvanceResponse.builder()
                .id(entity.getId())
                .employeeId(entity.getEmployee().getId())
                .employeeName(entity.getEmployee().getName())
                .amount(entity.getAmount())
                .advanceDate(entity.getAdvanceDate())
                .reason(entity.getReason())
                .build();
    }
}