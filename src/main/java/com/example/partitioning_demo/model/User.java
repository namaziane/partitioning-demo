package com.example.partitioning_demo.model;

import com.example.partitioning_demo.partitioning.PartitionAware;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users", schema = "partitioningg")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends PartitionAware<User> {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "users_seq", allocationSize = 1)
    private Long id;
 
    @Column(name = "first_name")
    private String firstName;
 
    @Column(name = "last_name")
    private String lastName;
 
    @Column(name = "registered_on")
    @CreationTimestamp
    private LocalDateTime createdOn = LocalDateTime.now();
     
}