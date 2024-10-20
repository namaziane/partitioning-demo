package com.example.partitioning_demo.model;

import com.example.partitioning_demo.partitioning.PartitionAware;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts", schema = "partitioningg")
@FilterDef(name = "partitionFilter", parameters = @ParamDef(name = "partitionKey", type = String.class))
@Filters({
    @Filter(name = "partitionFilter", condition = "partition_key = :partitionKey")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post extends PartitionAware {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq_gen")
    @SequenceGenerator(name = "post_seq_gen", sequenceName = "posts_seq", allocationSize = 1)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
