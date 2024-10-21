package com.example.partitioning_demo.service;

import com.example.partitioning_demo.partitionedentity.BalanceSheet;
import com.example.partitioning_demo.repository.BalanceSheetRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceSheetService {

    @Autowired
    private BalanceSheetRepository balanceSheetRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<BalanceSheet> findByEntityCode(String entityCode) {
        String hql = "FROM BalanceSheet WHERE entityCode = :entityCode";
        return entityManager.createQuery(hql, BalanceSheet.class)
                .setParameter("entityCode", entityCode)
                .getResultList();
    }

    public BalanceSheet saveWithFilter(BalanceSheet balanceSheet, String partitionKey) {
        // Activation du filtre de partitionnement manuellement
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("partitionFilter").setParameter("partitionKey", partitionKey);

        // Sauvegarder l'entité
        entityManager.persist(balanceSheet);

        return balanceSheet;
    }

    public void saveBalanceSheet(BalanceSheet balanceSheet, String partitionKey) {
        // Activer le filtre de partition avant l'opération de persistance
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("partitionFilter").setParameter("partitionKey", partitionKey);

        // Sauvegarder ou persister l'entité
        entityManager.persist(balanceSheet); // ou entityManager.save(balanceSheet);
    }
}