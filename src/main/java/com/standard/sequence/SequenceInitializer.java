package com.standard.sequence;

import com.standard.repository.SequenceRepository;
import com.standard.entity.SequenceEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SequenceInitializer {

    private final SequenceRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {

        createIfMissing(SequenceType.INVOICE);
        createIfMissing(SequenceType.ORDER);
    }

    private void createIfMissing(SequenceType type) {
        repository.findById(type.getDbName())
                .orElseGet(() ->
                        repository.save(
                                new SequenceEntity(type.getDbName(), 0L)
                        )
                );
    }
}

