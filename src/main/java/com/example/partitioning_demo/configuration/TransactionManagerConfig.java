package com.example.partitioning_demo.configuration;

import com.example.partitioning_demo.configuration.PartitionAwareEventListenerIntegrator;
import com.example.partitioning_demo.partitioning.PartitionAware;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class TransactionManagerConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        // autres configurations
        em.setDataSource(dataSource);
        em.setPackagesToScan(new String[]{"com.example.partitioning_demo"});
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.integrator_provider", (IntegratorProvider) () -> Collections.singletonList(PartitionAwareEventListenerIntegrator.INSTANCE));
//        properties.put("hibernate.integrator_provider", new CustomIntegratorProvider().getIntegrators());

        em.setJpaPropertyMap(properties);

        return em;
    }


    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory, EntityManager entityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setEntityManagerInitializer(entityManager2 -> {
//            User user = UserContext.getCurrent();
//            if (user != null) {
                entityManager2.unwrap(Session.class)
                        .enableFilter(PartitionAware.PARTITION_KEY)
                        .setParameter(
                                PartitionAware.PARTITION_KEY,
                                "Europe"
                        );
//            }
        });
        applyPartitionFilter(entityManager);

        return transactionManager;
    }

    private void applyPartitionFilter(EntityManager entityManager) {
        // Unwrap session Hibernate

        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("partitionFilter").setParameter("partitionKey", "Europe");


    }

}
