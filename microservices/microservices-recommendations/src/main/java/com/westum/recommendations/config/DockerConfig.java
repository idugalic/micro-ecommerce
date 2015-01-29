package com.westum.recommendations.config;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

@Configuration
@Profile("docker")
public class DockerConfig extends Neo4jConfiguration {
    public DockerConfig() {
        setBasePackage("com.westum.recommendations.model");
    }

    @Bean
    public GraphDatabaseService graphDatabaseService() {
        return new SpringRestGraphDatabase("http://192.168.59.103:7474/db/data/");
    }
}
