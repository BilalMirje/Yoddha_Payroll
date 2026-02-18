package com.standard.entity;

import com.standard.audit.Audit;
import com.standard.util.enums.ShiftType;
import com.standard.util.enums.WorkItemType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "event_work_items")
public class EventWorkItem extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "contractor_event_id", nullable = false)
    private ContractorEvent contractorEvent;

    @Column(nullable = false)
    private String description; // labour, spoons, plates, table...

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkItemType entryType; // LABOUR / MATERIAL

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShiftType shiftType; //Day/Night
    // --- Labour fields ---
    private Integer labourCount;
    private Double perLabourWage;

    // --- Material fields ---
    private Integer quantity;
    private Double unitCost;

    // --- Calculated ---
    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private Double paid;

    @Column(nullable = false)
    private Double remaining;
}