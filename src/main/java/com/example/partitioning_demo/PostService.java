package com.example.partitioning_demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void savePost(Post post) {
        entityManager.unwrap(Session.class).enableFilter("partitionFilter")
            .setParameter("partitionKey", "europe");

        entityManager.persist(post);
        entityManager.flush();
    }
}
