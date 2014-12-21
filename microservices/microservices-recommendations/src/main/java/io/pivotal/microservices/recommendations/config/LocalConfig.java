package io.pivotal.microservices.recommendations.config;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

@Configuration
@Profile("default")
public class LocalConfig {

    @Bean
    public GraphDatabaseService graphDatabaseService() {
        return new SpringRestGraphDatabase("http://movies.sb02.stations.graphenedb.com:24789/db/data/",
                "movies","jj5hGwRfAhaKJjSluJge");
//        return new GraphDatabaseFactory().newEmbeddedDatabase("/tmp/recommendations");
    }

}
