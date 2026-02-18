package com.standard.repository;


import com.standard.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractorRepository extends JpaRepository<Contractor, Long> {

    boolean existsByAadharNumber(String aadharNumber);
}
