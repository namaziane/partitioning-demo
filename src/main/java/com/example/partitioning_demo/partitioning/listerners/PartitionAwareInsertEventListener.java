package com.example.partitioning_demo.partitioning.listerners;

import com.example.partitioning_demo.partitioning.entities.PartitionAware;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.internal.FilterImpl;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class PartitionAwareInsertEventListener implements PreInsertEventListener {

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean onPreInsert(PreInsertEvent event) {
        Object entity = event.getEntity();

        setupFilter(event);

        if (entity instanceof PartitionAware) {
            PartitionAware partitionAware = (PartitionAware) entity;
            if (partitionAware.getPartitionKey() == null) {
                FilterImpl filter = (FilterImpl) event
                        .getSession()
                        .getEnabledFilter(PartitionAware.PARTITION_KEY);
                if (filter != null) {
                    ((PartitionAware)  entity ).setPartitionKey(
                            (String) filter
                                    .getParameter(PartitionAware.PARTITION_KEY)
                    );
                    event.getSession().persist(entity);
                    return true;
                }
            }
        }
        return true;
    }

    private void setupFilter(PreInsertEvent event) {
        event.getSession()
                .enableFilter(PartitionAware.PARTITION_KEY).setParameter(PartitionAware.PARTITION_KEY,"Europe");
    }
}