package com.example.partitioning_demo.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CustomTransactionManager {

    @PersistenceContext
    private EntityManager entityManager;

    public void enablePartitionFilter(String partitionKey) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("partitionFilter").setParameter("partitionKey", partitionKey);
    }

    public void processWithPartitionFilter(String partitionKey) {
        enablePartitionFilter(partitionKey);
        // Opérations de transaction, insertion, mise à jour ou suppression
    }
}