package org.springframework.cloud.neo4j;

import org.springframework.cloud.service.BaseServiceInfo;

public class GraphDatabaseServiceInfo extends BaseServiceInfo {

    private final String neo4jUri;
    private final String neo4jUsername;
    private final String neo4jPassword;

    public GraphDatabaseServiceInfo(String id, String neo4jUsername, String neo4jPassword, String neo4jUri) {
        super(id);
        this.neo4jUsername = neo4jUsername;
        this.neo4jPassword = neo4jPassword;
        this.neo4jUri = neo4jUri;
    }
    @ServiceProperty
	public String getNeo4jUri() {
		return neo4jUri;
	}
    @ServiceProperty
	public String getNeo4jUsername() {
		return neo4jUsername;
	}
    @ServiceProperty
	public String getNeo4jPassword() {
		return neo4jPassword;
	}

   }
