package com.standard.repository;

import com.standard.entity.ContractorEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContractorEventRepository extends JpaRepository<ContractorEvent, Long> {

    List<ContractorEvent> findByEventId(Long eventId);
    Page<ContractorEvent> findByEventId(Long eventId, Pageable pageable);
    List<ContractorEvent> findByEventId(Long eventId, Sort sort);

    @Query("select coalesce(sum(c.totalAmount),0) from ContractorEvent c where c.contractor.id = :contractorId")
    Optional<Double> sumTotalAmountByContractorId(Long contractorId);

    @Query("select coalesce(sum(c.totalPaid),0) from ContractorEvent c where c.contractor.id = :contractorId")
    Optional<Double> sumTotalPaidByContractorId(Long contractorId);
    boolean existsByEvent_IdAndContractor_Id(Long eventId, Long contractorId);

    List<ContractorEvent> findByContractor_Id(Long contractorId);


}