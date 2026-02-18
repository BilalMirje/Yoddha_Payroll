package com.standard.repository;


import com.standard.entity.ContractorPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractorPaymentRepository extends JpaRepository<ContractorPayment, Long> {

    List<ContractorPayment> findByContractorEvent_Contractor_IdOrderByPaymentDateAsc(Long contractorId);

    List<ContractorPayment> findByContractorEvent_IdOrderByPaymentDateAsc(Long contractorEventId);
}
