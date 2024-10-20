package com.example.partitioning_demo.partitioning;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.HibernateException;
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
//        // Logique pour traiter l'événement de persistence avec partitioning
//        Object entity = event.getObject();
//        if (entity instanceof PartitionedEntity) {
//            PartitionedEntity partitionedEntity = (PartitionedEntity) entity;
//            // Gérer la clé de partition dans la sauvegarde
//        }
//        entityManager.unwrap(Session.class).enableFilter("partitionFilter")
//                .setParameter("partitionKey", PartitionAware.PARTITION_KEY);

         Object entity =  event.getObject();

        if (entity instanceof PartitionAware partitionAware) {
            if (partitionAware.getPartitionKey() == null) {
                FilterImpl partitionKeyFilter = (FilterImpl) event
                        .getSession()
                        .getEnabledFilter(PartitionAware.PARTITION_KEY);
                partitionAware.setPartitionKey(
                        (String) partitionKeyFilter
                                .getParameter(PartitionAware.PARTITION_KEY)
                );
            }
        }


//        if (entity instanceof PartitionAware partitionAware) {
//            if (partitionAware.getPartitionKey() == null) {
//                FilterImpl partitionKeyFilter = (FilterImpl) event
//                        .getSession()
//                        .getEnabledFilter("partition_key");
//
//
//
//
//        ((PartitionAware) entity).setPartitionKey(
//                        (String) partitionKeyFilter
//                                .getParameter(PartitionAware.PARTITION_KEY)
//                );
//            }
//        }

    }

    @Override
    public void onPersist(PersistEvent persistEvent, PersistContext persistContext) throws HibernateException {
        onPersist(persistEvent);
    }
//
//    @Override
//    public void onPersist(PersistEvent event, Map copiedAlready) throws HibernateException {
//        onPersist(event);
//    }
}
