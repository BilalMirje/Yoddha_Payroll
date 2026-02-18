package com.standard.entity;

import com.standard.audit.Audit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contact;

    // optional
    private String email;

    @Column(nullable = false)
    private Double salary;

    @Column(nullable = false)
    private LocalTime inTime;

    @Column(nullable = false)
    private LocalTime outTime;

    @Column(nullable = false, unique = true)
    private String aadharNumber;

    // optional — S3 image URL
    private String profilePhoto;

    @Column(nullable = false)
    private LocalDate dateOfJoining;
}
