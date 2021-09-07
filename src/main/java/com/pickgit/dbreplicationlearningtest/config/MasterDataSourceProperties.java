package com.pickgit.dbreplicationlearningtest.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "datasource")
public class MasterDataSourceProperties {

    private String url;
    private String username;
    private String password;

    private Map<String, Slave> slaves;

    public MasterDataSourceProperties() {
    }

    public MasterDataSourceProperties(
        Map<String, Slave> slaves
    ) {
        this.slaves = slaves;
    }

    public MasterDataSourceProperties(String url, String username, String password) {
        this.slaves = new HashMap<>();

        this.url = url;
        this.username = username;
        this.password = password;
    }

    public MasterDataSourceProperties(
        Map<String, Slave> slaves, String url, String username, String password) {
        this.slaves = slaves;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Map<String, Slave> getSlaves() {
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

    public static class Slave {

        private String name;
        private String url;

        public Slave() {
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
