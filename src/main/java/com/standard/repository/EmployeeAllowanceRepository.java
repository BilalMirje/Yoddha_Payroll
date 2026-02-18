package com.standard.repository;

import com.standard.entity.EmployeeAllowance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeAllowanceRepository extends JpaRepository<EmployeeAllowance, Long> {

    List<EmployeeAllowance> findByEmployeeId(Long employeeId);
    List<EmployeeAllowance> findByEmployeeId(Long employeeId, Sort sort);
    Page<EmployeeAllowance> findByEmployeeId(Long employeeId, Pageable pageable);
    @Query("SELECT COALESCE(SUM(a.amount), 0) FROM EmployeeAllowance a " +
            "WHERE a.employee.id = :employeeId AND a.allowanceDate BETWEEN :start AND :end")
    Double findTotalAllowance(Long employeeId, LocalDate start, LocalDate end);

}