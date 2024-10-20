package com.example.partitioning_demo;

import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name = "posts")
@FilterDef(name = "partitionFilter", parameters = @ParamDef(name = "partitionKey", type = String.class))
@Filters({
    @Filter(name = "partitionFilter", condition = "partition_key = :partitionKey")
})
public class Post extends PartitionAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "partition_key")
    private String partitionKey;

    // Getters et Setters
//    @Override
//    public String getPartitionKey() {
//        return this.partitionKey;
//    }

//    @Override
//    public void setPartitionKey(String partitionKey) {
//        this.partitionKey = partitionKey;
//    }
}
