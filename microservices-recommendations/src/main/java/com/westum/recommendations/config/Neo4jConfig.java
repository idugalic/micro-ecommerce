package com.westum.recommendations.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

@Configuration
@EnableNeo4jRepositories(basePackages="com.westum.recommendations.repositories")
public class Neo4jConfig extends Neo4jConfiguration {
    public Neo4jConfig() {
        setBasePackage("com.westum.recommendations.model");
    }

}
