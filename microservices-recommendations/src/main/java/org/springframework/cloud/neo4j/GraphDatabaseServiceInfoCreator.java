package org.springframework.cloud.neo4j;

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

import java.util.Map;

public class GraphDatabaseServiceInfoCreator extends CloudFoundryServiceInfoCreator<GraphDatabaseServiceInfo> {
	public GraphDatabaseServiceInfoCreator() {
		super(new Tags("neo4j"), "neo4j");
	}

	@Override
	public GraphDatabaseServiceInfo createServiceInfo(Map<String, Object> serviceData) {
		@SuppressWarnings("unchecked")
		Map<String, Object> credentials = (Map<String, Object>) serviceData.get("credentials");

		String id = (String) serviceData.get("name");
		String neo4jUri = getStringFromCredentials(credentials, "neo4jUri");
		String neo4jPassword = getStringFromCredentials(credentials, "neo4jPassword");
		String neo4jUsername = getStringFromCredentials(credentials, "neo4jUsername");

		return new GraphDatabaseServiceInfo(id, neo4jUsername, neo4jPassword, neo4jUri);
	}
}
