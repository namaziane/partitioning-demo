package com.example.partitioning_demo.partitionedentity;

import jakarta.persistence.*;

@Entity
public class BalanceSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_code")
    private String entityCode;

    // Autres champs...

    @PrePersist
    public void onPrePersist() {
        // Code qui doit être exécuté avant la persistance
        System.out.println("Audit: Préparation à la persistance de l'entité BalanceSheet");
    }
}
