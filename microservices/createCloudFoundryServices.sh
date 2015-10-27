#!/bin/bash
cf cups config-server -p '{"uri":"http://config-server-idugalic.cfapps.io"}'
cf cups eureka -p '{"uri":"http://eureka-idugalic.cfapps.io"}'
cf cups recommendations-db -p '{"neo4jUri":"http://idugalicmicroservices.sb04.stations.graphenedb.com:24789/db/data/","neo4jUsername":"Idugalic_microservices","neo4jPassword":"mlzkAME4f85viYEwgSts"}'