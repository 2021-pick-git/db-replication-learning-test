package com.pickgit.dbreplicationlearningtest.config;

import static java.util.stream.Collectors.toList;

import com.pickgit.dbreplicationlearningtest.config.replication.Slaves;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    private Slaves slaves;

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);

        List<SlaveDataSourceProperties> replicas = targetDataSources.keySet().stream()
            .map(ReplicaDataSourceProperties.class::cast)
            .filter(replicaDataSourceProperties -> !replicaDataSourceProperties.isMaster())
            .map(SlaveDataSourceProperties.class::cast)
            .collect(toList());

        this.slaves = new Slaves(replicas);
    }

    @Override
    protected String determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (isReadOnly) {
            return slaves.getNextName();
        }

        return "master";
    }
}
