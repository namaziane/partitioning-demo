package com.example.partitioning_demo.partitioning.listerners;

import com.example.partitioning_demo.partitioning.entities.PartitionAware;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.PersistContext;
import org.hibernate.event.spi.PersistEvent;
import org.hibernate.event.spi.PersistEventListener;
import org.hibernate.internal.FilterImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartitionAwarePersistEventListener implements PersistEventListener {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void onPersist(PersistEvent event) throws HibernateException {
        Object entity =  event.getObject();

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
//                    event.getSession().persist(entity);
                }
            }
        }


//        event.getSession()
//                .enableFilter(PartitionAware.PARTITION_KEY).setParameter(PartitionAware.PARTITION_KEY,"Europe");
//
//        FilterImpl partitionKeyFilter = (FilterImpl) event
//                .getSession()
//                .getEnabledFilter(PartitionAware.PARTITION_KEY);
//
//         ((PartitionAware)  entity ).setPartitionKey(
//                        (String) partitionKeyFilter
//                                .getParameter(PartitionAware.PARTITION_KEY)
//                );

    }

    private void setupFilter(PersistEvent event) {
        event.getSession()
                .enableFilter(PartitionAware.PARTITION_KEY).setParameter(PartitionAware.PARTITION_KEY,"Europe");
    }

    @Override
    public void onPersist(PersistEvent persistEvent, PersistContext persistContext) throws HibernateException {
        onPersist(persistEvent);
    }
}
