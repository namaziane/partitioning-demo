package com.example.partitioning_demo.configuration;

import com.example.partitioning_demo.partitioning.listerners.PartitionAwareInsertEventListener;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class PartitionAwareEventListenerIntegrator implements Integrator {
    
    public static final PartitionAwareEventListenerIntegrator INSTANCE = new PartitionAwareEventListenerIntegrator();

    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
//        eventListenerRegistry.getEventListenerGroup(EventType.PERSIST).appendListener(new PartitionAwarePersistEventListener());
        eventListenerRegistry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(new PartitionAwareInsertEventListener());
//        System.out.println("PartitionAwareEventListenerIntegrator is being integrated.");
//        EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);

//        PartitionAwareEventListener listener = new PartitionAwareEventListener();
//
//        // Enregistrer pour PERSIST
////        eventListenerRegistry.getEventListenerGroup(EventType.PERSIST)
////                .appendListener(listener);
//
//        // Enregistrer pour PRE_INSERT
//        eventListenerRegistry.getEventListenerGroup(EventType.PRE_INSERT)
//                .appendListener(listener);

        System.out.println("PartitionAwareEventListener has been integrated for both PERSIST and PRE_INSERT.");
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        System.out.println("PartitionAwareEventListenerIntegrator is being disintegrated.");
    }
}
