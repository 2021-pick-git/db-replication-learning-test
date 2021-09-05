package com.pickgit.dbreplicationlearningtest.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "datasource.master")
public class MasterDataSourceProperties implements ReplicaDataSourceProperties {

    private String url;
    private String username;
    private String password;
    private final Map<String, SlaveDataSourceProperties> slaves;

    public MasterDataSourceProperties() {
        this(new HashMap<>());
    }

    public MasterDataSourceProperties(
        Map<String, SlaveDataSourceProperties> slaves) {
        this.slaves = slaves;
    }

    @Override
    public String getName() {
        return "master";
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public boolean isMaster() {
        return true;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Map<String, SlaveDataSourceProperties> getSlaves() {
        return slaves;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class SlaveDataSourceProperties {

        private final String name;
        private final String url;

        public SlaveDataSourceProperties(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}
