package com.example.partitioning_demo.partitioning.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PartitionedEntity {

    @Column(name = "entity_code", nullable = false)
    private String partitionKey;

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    // Autres champs communs, comme des métadonnées par exemple
}
