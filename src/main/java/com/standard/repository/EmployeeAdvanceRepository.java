package com.standard.repository;

import com.standard.entity.EmployeeAdvance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface EmployeeAdvanceRepository extends JpaRepository<EmployeeAdvance, Long> {
    @Query("SELECT COALESCE(SUM(a.amount), 0) FROM EmployeeAdvance a " +
            "WHERE a.employee.id = :employeeId AND a.advanceDate BETWEEN :start AND :end")
    Double findTotalAdvance(Long employeeId, LocalDate start, LocalDate end);

}
