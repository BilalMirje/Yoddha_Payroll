package com.standard.repository;

import com.standard.entity.EmployeeAttendance;
import com.standard.util.enums.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface EmployeeAttendanceRepository extends JpaRepository<EmployeeAttendance, Long> {

    boolean existsByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate date);

    Optional<EmployeeAttendance> findByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate date);

    // ---- DATE FILTER ----
    List<EmployeeAttendance> findByAttendanceDate(LocalDate date);

    Page<EmployeeAttendance> findByAttendanceDate(LocalDate date, Pageable pageable);

    List<EmployeeAttendance> findByAttendanceDate(LocalDate date, Sort sort);

    // ---- COUNT FOR SUMMARY ----
    long countByEmployeeIdAndStatusAndAttendanceDateBetween(
            Long employeeId,
            AttendanceStatus status,
            LocalDate start,
            LocalDate end
    );

    void deleteByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate date);

    void deleteByAttendanceDate(LocalDate date);
}

