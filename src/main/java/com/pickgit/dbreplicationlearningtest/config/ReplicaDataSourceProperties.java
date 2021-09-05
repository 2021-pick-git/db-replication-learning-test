package com.pickgit.dbreplicationlearningtest.config;

public interface ReplicaDataSourceProperties {

    String getName();
    String getUrl();
    boolean isMaster();
}
