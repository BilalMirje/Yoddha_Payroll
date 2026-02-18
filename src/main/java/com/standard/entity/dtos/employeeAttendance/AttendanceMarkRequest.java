package com.standard.entity.dtos.employeeAttendance;

import com.standard.util.enums.AttendanceStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceMarkRequest {
    private Long employeeId;
//    private LocalDate date;
    private AttendanceStatus status;
}

