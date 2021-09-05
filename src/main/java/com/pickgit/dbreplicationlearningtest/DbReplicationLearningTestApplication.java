package com.pickgit.dbreplicationlearningtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class DbReplicationLearningTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbReplicationLearningTestApplication.class, args);
    }

}
