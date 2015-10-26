package com.westum.recommendations.config;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.netflix.discovery.EurekaClientConfig;

@Configuration
@Profile("cloud")
public class CloudConfig extends AbstractCloudConfig {

    @Bean
    GraphDatabaseService graphDatabaseService() {
        return connectionFactory().service(GraphDatabaseService.class);
    }

    @Bean
    EurekaClientConfig eurekaClientConfig() {
        return connectionFactory().service(EurekaClientConfig.class);
    }
}
