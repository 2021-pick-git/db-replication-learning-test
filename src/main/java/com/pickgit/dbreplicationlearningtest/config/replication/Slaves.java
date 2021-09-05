package com.pickgit.dbreplicationlearningtest.config.replication;

import com.pickgit.dbreplicationlearningtest.config.SlaveDataSourceProperties;
import java.util.List;

public class Slaves {

    private final SlaveDataSourceProperties[] value;
    private int counter = 0;

    public Slaves(List<SlaveDataSourceProperties> slaveDataSourceProperties) {
        this(slaveDataSourceProperties.toArray(SlaveDataSourceProperties[]::new));
    }

    public Slaves(SlaveDataSourceProperties[] value) {
        this.value = value;
    }

    public String getNextName() {
        int index = counter;
        counter = (counter + 1) % value.length;
        return value[index].getName();
    }
}
