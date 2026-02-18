package com.standard.entity.dtos.employeeAttendance;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceSummaryResponse {
    private Long employeeId;
    private String employeeName;
    private long presentCount;
    private long absentCount;
}
