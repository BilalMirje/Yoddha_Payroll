package com.standard.entity;

import com.standard.audit.Audit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contractor_advances")
public class ContractorAdvance extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contractor_event_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)

    private ContractorEvent contractorEvent;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate advanceDate;

    @Column(nullable = false, length = 500)
    private String reason;
}