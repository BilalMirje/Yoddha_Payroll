package com.standard.repository;

import com.standard.entity.EventWorkItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventWorkItemRepository extends JpaRepository<EventWorkItem, Long> {

    List<EventWorkItem> findByContractorEventId(Long contractorEventId);

    // PAGINATION
    Page<EventWorkItem> findByContractorEventId(Long contractorEventId, Pageable pageable);

    // SORTING
    List<EventWorkItem> findByContractorEventId(Long contractorEventId, Sort sort);
}
