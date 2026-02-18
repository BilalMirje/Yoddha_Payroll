package com.standard.entity.dtos.employeeAttendance;


import com.standard.util.enums.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceResponse {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private LocalDate attendanceDate;
    private AttendanceStatus status;
}
