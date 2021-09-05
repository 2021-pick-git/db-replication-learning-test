package com.pickgit.dbreplicationlearningtest.config;

import static java.util.stream.Collectors.toList;

import com.pickgit.dbreplicationlearningtest.config.replication.SlaveNames;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    private SlaveNames slaveNames;

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);

        List<String> replicas = targetDataSources.keySet().stream()
            .map(Object::toString)
            .filter(string -> string.contains("slave"))
            .collect(toList());

        this.slaveNames = new SlaveNames(replicas);
    }

    @Override
    protected String determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (isReadOnly) {
            return slaveNames.getNextName();
        }

        return "master";
    }
}
