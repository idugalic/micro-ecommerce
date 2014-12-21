package org.springframework.cloud.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.cloud.service.AbstractServiceConnectorCreator;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

public class GraphDatabaseServiceConnectorCreator extends AbstractServiceConnectorCreator<GraphDatabaseService, GraphDatabaseServiceInfo> {
    @Override
    public GraphDatabaseService create(GraphDatabaseServiceInfo neo4JServiceInfo, ServiceConnectorConfig serviceConnectorConfig) {
        return new SpringRestGraphDatabase(createUri(neo4JServiceInfo),
                neo4JServiceInfo.getUsername(),
                neo4JServiceInfo.getPassword());
    }

    private String createUri(GraphDatabaseServiceInfo neo4JServiceInfo) {
        return new StringBuilder("http://")
                .append(neo4JServiceInfo.getHost())
                .append(":")
                .append(neo4JServiceInfo.getHttpPort())
                .append("/db/data")
                .toString();
    }
}
