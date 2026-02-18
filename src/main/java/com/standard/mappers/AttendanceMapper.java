package com.standard.mappers;

import com.standard.entity.Employee;
import com.standard.entity.dtos.employeeAttendance.AttendanceSummaryResponse;
import org.springframework.stereotype.Component;


import com.standard.entity.EmployeeAttendance;
import com.standard.entity.dtos.employeeAttendance.AttendanceResponse;


@Component
public class AttendanceMapper {

    public AttendanceSummaryResponse toSummary(Employee employee,
                                               long present,
                                               long absent) {
        return AttendanceSummaryResponse.builder()
                .employeeId(employee.getId())
                .employeeName(employee.getName())
                .presentCount(present)
                .absentCount(absent)
                .build();
    }

    public AttendanceResponse toResponse(EmployeeAttendance entity) {
        return AttendanceResponse.builder()
                .id(entity.getId())
                .employeeId(entity.getEmployee().getId())
                .employeeName(entity.getEmployee().getName())
                .attendanceDate(entity.getAttendanceDate())
                .status(entity.getStatus())
                .build();
    }
}

