package com.standard.repository;

import com.standard.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByAadharNumber(String aadharNumber);

    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findByNameContainingIgnoreCase(String name, Sort sort);
    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);
}