package com.standard.service.helper;

import com.standard.entity.ContractorEvent;
import com.standard.entity.EventWorkItem;
import com.standard.repository.ContractorEventRepository;
import com.standard.repository.EventWorkItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractorEventTotalHelper {

    private final EventWorkItemRepository workItemRepository;
    private final ContractorEventRepository contractorEventRepository;

    public void recalculateTotals(ContractorEvent ce){

        double totalAmount = workItemRepository.findByContractorEventId(ce.getId())
                .stream()
                .mapToDouble(EventWorkItem::getTotalAmount)
                .sum();

        double totalPaid = workItemRepository.findByContractorEventId(ce.getId())
                .stream()
                .mapToDouble(EventWorkItem::getPaid)
                .sum();

        ce.setTotalAmount(totalAmount);
        ce.setTotalPaid(totalPaid);
        ce.setTotalRemaining(totalAmount - totalPaid);

        contractorEventRepository.save(ce);
    }
}
