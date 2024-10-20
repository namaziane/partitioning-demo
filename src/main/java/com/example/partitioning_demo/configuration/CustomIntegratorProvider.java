package com.example.partitioning_demo.configuration;

import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;

import java.util.List;

public class CustomIntegratorProvider implements IntegratorProvider {

    @Override
    public List<Integrator> getIntegrators() {
        // Return the custom partition-aware integrator
        return List.of(PartitionAwareEventListenerIntegrator.INSTANCE);
    }
}