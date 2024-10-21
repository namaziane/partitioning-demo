package com.example.partitioning_demo.partitioning.listerners;

import com.example.partitioning_demo.partitioning.entities.PartitionAware;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.event.spi.*;
import org.hibernate.internal.FilterImpl;
import org.springframework.stereotype.Service;

@Service
public class PartitionAwareEventListener implements PersistEventListener, PreInsertEventListener {

    @PersistenceContext
    private EntityManager entityManager;

    private void setupFilter(PreInsertEvent event) {
        event.getSession()
                .enableFilter(PartitionAware.PARTITION_KEY).setParameter(PartitionAware.PARTITION_KEY,"Europe");
    }
    @Override
    public void onPersist(PersistEvent event) throws HibernateException {
        setupFilterPersistEvent(event);
        handleEvent(event.getObject(), event.getSession());
    }

    private void setupFilterPersistEvent(PersistEvent event) {
        event.getSession()
                .enableFilter(PartitionAware.PARTITION_KEY).setParameter(PartitionAware.PARTITION_KEY,"Europe");
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
//        setupFilter(event);
        handleEvent(event.getEntity(), event.getSession());
        return false;
    }

    private void handleEvent(Object entity, Session session) {
        if (entity instanceof PartitionAware) {
            PartitionAware partitionAware = (PartitionAware) entity;
            if (partitionAware.getPartitionKey() == null) {
                FilterImpl filter = (FilterImpl) session.getEnabledFilter(PartitionAware.PARTITION_KEY);
                if (filter != null) {
                    partitionAware.setPartitionKey((String) filter.getParameter(PartitionAware.PARTITION_KEY));
                }
            }
        }

        session.enableFilter(PartitionAware.PARTITION_KEY).setParameter(PartitionAware.PARTITION_KEY, "Europe");
    }

    @Override
    public void onPersist(PersistEvent persistEvent, PersistContext  persistContext) throws HibernateException {
        onPersist(persistEvent);
    }
}
