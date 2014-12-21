package org.springframework.cloud.neo4j;

import org.springframework.cloud.service.BaseServiceInfo;

public class GraphDatabaseServiceInfo extends BaseServiceInfo {

    private final int httpPort;
    private final int httpsPort;
    private final String host;
    private final String username;
    private final String password;

    public GraphDatabaseServiceInfo(String id, String host, String username, String password, int httpPort, int httpsPort) {
        super(id);
        this.host = host;
        this.username = username;
        this.password = password;
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;
    }

    @ServiceProperty
    public String getHost() {
        return host;
    }

    @ServiceProperty
    public String getUsername() {
        return username;
    }

    @ServiceProperty
    public String getPassword() {
        return password;
    }

    @ServiceProperty
    public int getHttpPort() {
        return httpPort;
    }

    @ServiceProperty
    public int getHttpsPort() {
        return httpsPort;
    }
}
