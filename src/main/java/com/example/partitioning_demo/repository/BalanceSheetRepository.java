package com.example.partitioning_demo.repository;

import com.example.partitioning_demo.model.BalanceSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceSheetRepository extends JpaRepository<BalanceSheet, Long> {
    // Vous pouvez ajouter ici des méthodes personnalisées si nécessaire
    List<BalanceSheet> findByEntityCode(String entityCode);
}