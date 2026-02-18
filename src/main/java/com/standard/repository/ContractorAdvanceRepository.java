package com.standard.repository;

import com.standard.entity.ContractorAdvance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContractorAdvanceRepository extends JpaRepository<ContractorAdvance, Long> {
    @Query("select coalesce(sum(a.amount),0) from ContractorAdvance a where a.contractorEvent.id = :contractorEventId")
    Optional<Double> sumByContractorEventId(Long contractorEventId);

    @Query("select coalesce(sum(a.amount),0) from ContractorAdvance a where a.contractorEvent.contractor.id = :contractorId")
    Optional<Double> sumByContractorId(Long contractorId);

    List<ContractorAdvance> findByContractorEvent_Contractor_Id(Long contractorId);



}