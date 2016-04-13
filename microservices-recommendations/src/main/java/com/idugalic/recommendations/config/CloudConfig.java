package com.idugalic.recommendations.config;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class CloudConfig extends AbstractCloudConfig {

    @Bean
    GraphDatabaseService graphDatabaseService() {
        return connectionFactory().service(GraphDatabaseService.class);
    }
}
