package com.example.partitioning_demo.service;

import com.example.partitioning_demo.model.Post;
import com.example.partitioning_demo.model.User;
import com.example.partitioning_demo.partitioning.PartitionAware;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.internal.FilterImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    @Transactional
    public void saveUserAndPosts(User user) {
        entityManager.unwrap(Session.class).enableFilter("partitionFilter")
                .setParameter("partitionKey", "Europe");

//         final User vlad = new User();
//             vlad.setFirstName("Vlad");
//             vlad.setLastName("Mihalcea");
//             vlad.setPartition(Partition.EUROPE);

         entityManager.persist(user);
//         entityManager.flush();

//         UserContext.logIn(vlad);

//         List<Post> posts = LongStream.rangeClosed(1, 2)
//                 .mapToObj(postId -> {
//                     final Post post1 = new Post();
//                     post1.setUser(user);
//                     post1.setTitle(String.format(
//                             "High-Performance Java Persistence - Part %d",
//                             postId
//                     ));
//                     return post1;
//                 }).collect(Collectors.toList());
//        List<Post> mutablePosts = new ArrayList<>(posts);
//        entityManager.persist(mutablePosts);
//        entityManager.flush();
    }

}
