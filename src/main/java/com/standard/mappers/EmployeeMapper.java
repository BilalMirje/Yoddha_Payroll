package com.standard.mappers;


import com.standard.entity.Employee;
import com.standard.entity.dtos.employeeDtos.EmployeeRequest;
import com.standard.entity.dtos.employeeDtos.EmployeeResponse;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequest dto){
        return Employee.builder()
                .name(dto.getName())
                .contact(dto.getContact())
                .email(dto.getEmail())
                .salary(dto.getSalary())
                .inTime(dto.getInTime())
                .outTime(dto.getOutTime())
                .aadharNumber(dto.getAadharNumber())
                .dateOfJoining(dto.getDateOfJoining())
                .build();
    }

    public void updateEntity(Employee entity, EmployeeRequest dto){
        entity.setName(dto.getName());
        entity.setContact(dto.getContact());
        entity.setEmail(dto.getEmail());
        entity.setSalary(dto.getSalary());
        entity.setInTime(dto.getInTime());
        entity.setOutTime(dto.getOutTime());
        entity.setAadharNumber(dto.getAadharNumber());
        entity.setDateOfJoining(dto.getDateOfJoining());
    }

    public EmployeeResponse toResponse(Employee entity){
        return EmployeeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .contact(entity.getContact())
                .email(entity.getEmail())
                .salary(entity.getSalary())
                .inTime(entity.getInTime())
                .outTime(entity.getOutTime())
                .aadharNumber(entity.getAadharNumber())
                .profilePhoto(entity.getProfilePhoto())
                .dateOfJoining(entity.getDateOfJoining())
                .build();
    }
}
