package org.springframework.cloud.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.cloud.service.AbstractServiceConnectorCreator;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

public class GraphDatabaseServiceConnectorCreator extends AbstractServiceConnectorCreator<GraphDatabaseService, GraphDatabaseServiceInfo> {
    @Override
    public GraphDatabaseService create(GraphDatabaseServiceInfo neo4JServiceInfo, ServiceConnectorConfig serviceConnectorConfig) {
        return new SpringRestGraphDatabase(neo4JServiceInfo.getNeo4jUri(),
                neo4JServiceInfo.getNeo4jUsername(),
                neo4JServiceInfo.getNeo4jPassword());
    }

}
