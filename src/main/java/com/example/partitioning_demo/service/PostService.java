package com.example.partitioning_demo.service;

import com.example.partitioning_demo.partitionedentity.Post;
import com.example.partitioning_demo.partitionedentity.User;
import com.example.partitioning_demo.partitioning.entities.PartitionAware;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.internal.FilterImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.LongStream;

@Service
public class PostService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void savePost(Post post) {
        entityManager.unwrap(Session.class).enableFilter("partitionFilter")
            .setParameter("partitionKey", "Europe");

        FilterImpl partitionKeyFilter = (FilterImpl) entityManager
                .unwrap(Session.class)
                .getEnabledFilter(PartitionAware.PARTITION_KEY);

        post.setPartitionKey(
                (String) partitionKeyFilter
                        .getParameter(PartitionAware.PARTITION_KEY)
        );

        entityManager.persist(post);
        entityManager.flush();
    }


    public void printDataSourceInfo() {
        Object jdbcUrl = entityManager.getEntityManagerFactory()
                .getProperties()
                .get("jakarta.persistence.jdbc.url");
        Object username = entityManager.getEntityManagerFactory()
                .getProperties()
                .get("jakarta.persistence.jdbc.user");
        System.out.println("DataSource URL: " + jdbcUrl);
        System.out.println("DataSource User: " + username);
    }
    public void printDataSourceConfig2() {
        HikariDataSource dataSource = (HikariDataSource) entityManager.getEntityManagerFactory()
                .getProperties()
                .get("jakarta.persistence.nonJtaDataSource");

        if (dataSource != null) {
            System.out.println("DataSource Configuration:");
            System.out.println("JDBC URL: " + dataSource.getJdbcUrl());
            System.out.println("Username: " + dataSource.getUsername());
            System.out.println("Driver Class Name: " + dataSource.getDriverClassName());
            System.out.println("Maximum Pool Size: " + dataSource.getMaximumPoolSize());
            System.out.println("Minimum Idle: " + dataSource.getMinimumIdle());
        } else {
            System.out.println("DataSource is not initialized.");
        }
    }

    @Transactional
    public void saveUserAndPosts(User user) {
        entityManager.unwrap(Session.class).enableFilter("partitionFilter")
                .setParameter("partitionKey", "Europe");

//         final User vlad = new User();
//             vlad.setFirstName("Vlad");
//             vlad.setLastName("Mihalcea");
//             vlad.setPartition(Partition.EUROPE);
        printDataSourceConfig2();
        entityManager.persist(user);
//        entityManager.flush();
        User aaa = entityManager.find(User.class, user.getId());
//         entityManager.flush();

//         UserContext.logIn(vlad);

          LongStream.rangeClosed(1, 2)
                 .mapToObj(postId -> {
                     final Post post1 = new Post();
                     post1.setUser(user);
                     post1.setTitle(String.format(
                             "High-Performance Java Persistence - Part %d",
                             postId
                     ));
                     return post1;
                 }).forEach(it->{
                      entityManager.persist(it);
                  });
//        List<Post> mutablePosts = new ArrayList<>(posts);
//        entityManager.flush();
    }

}
