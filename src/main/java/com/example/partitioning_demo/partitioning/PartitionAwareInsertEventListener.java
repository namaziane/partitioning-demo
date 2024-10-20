package com.example.partitioning_demo.partitioning;

import com.example.partitioning_demo.partitioning.PartitionAware;
import org.hibernate.Filter;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;

public class PartitionAwareInsertEventListener implements PreInsertEventListener {

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        Object entity = event.getEntity();

        if (entity instanceof PartitionAware) {
            PartitionAware partitionAware = (PartitionAware) entity;
            if (partitionAware.getPartitionKey() == null) {
                Filter filter = event.getSession().getEnabledFilter("partitionFilter");
                if (filter != null) {
//                    partitionAware.setPartitionKey((String) filter.getParameter("partitionKey"));
                }
            }
        }
        return false;
    }
}