package com.idugalic.recommendations.config;

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
		setBasePackage("com.idugalic.recommendations.model");
	}

	@Bean
	public GraphDatabaseService graphDatabaseService() {
		return new SpringRestGraphDatabase("http://neo4j:7474/db/data/");
	}
}
