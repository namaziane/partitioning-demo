package com.example.partitioning_demo.partitioning;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.event.spi.PersistContext;
import org.hibernate.event.spi.PersistEvent;
import org.hibernate.event.spi.PersistEventListener;
import org.hibernate.internal.FilterImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartitionAwarePersistEventListener implements PersistEventListener {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void onPersist(PersistEvent event) throws HibernateException {

//         Object entity =  event.getObject();
//
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

    @Override
    public void onPersist(PersistEvent persistEvent, PersistContext persistContext) throws HibernateException {
        onPersist(persistEvent);
    }
}
