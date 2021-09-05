package com.pickgit.dbreplicationlearningtest.config;

import static java.util.stream.Collectors.toMap;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(MasterDataSourceProperties.class)
public class DataSourceConfig {

    private final MasterDataSourceProperties dataSourceProperties;
    private final JpaProperties jpaProperties;

    public DataSourceConfig(
        MasterDataSourceProperties dataSourceProperties,
        JpaProperties jpaProperties) {
        this.dataSourceProperties = dataSourceProperties;
        this.jpaProperties = jpaProperties;
    }

    @Bean
    public DataSource routingDataSource() {

        DataSource master = createDataSource(dataSourceProperties.getUrl());

        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("master", master);
        dataSourceProperties.getSlaves()
            .forEach((key, value) -> dataSources
                .put(value.getName(), createDataSource(value.getUrl())));

//        Map<Object, Object> dataSources = Stream.of(
//            List.of(master).stream(),
//            dataSourceProperties.getSlaves().keySet().stream()
//        )
//            .flatMap(Function.identity())
//            .map(ReplicaDataSourceProperties.class::cast)
//            .collect(toMap(
//                ReplicaDataSourceProperties::getName,
//                replicaDataSourceProperties -> createDataSource(
//                    replicaDataSourceProperties.getUrl())
//            ));

        ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();
        replicationRoutingDataSource.setDefaultTargetDataSource(dataSources.get("master"));
        replicationRoutingDataSource.setTargetDataSources(dataSources);

        return replicationRoutingDataSource;
    }

    private DataSource createDataSource(String url) {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .url(url)
            .driverClassName("org.mariadb.jdbc.Driver")
            .username(dataSourceProperties.getUsername())
            .password(dataSourceProperties.getPassword())
            .build();
    }

    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager(
        EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        EntityManagerFactoryBuilder entityManagerFactoryBuilder =
            createEntityManagerFactoryBuilder(jpaProperties);

        return entityManagerFactoryBuilder.dataSource(dataSource())
            .packages("com.pickgit.dbreplicationlearningtest")
            .build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(
        JpaProperties jpaProperties) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        return new EntityManagerFactoryBuilder(vendorAdapter, jpaProperties.getProperties(), null);
    }
}
