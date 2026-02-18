package com.standard.entity.dtos.employeeDtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

    private Long id;
    private String name;
    private String contact;
    private String email;
    private Double salary;
    private LocalTime inTime;
    private LocalTime outTime;
    private String aadharNumber;
    private String profilePhoto;
    private LocalDate dateOfJoining;
}
