#!/bin/bash
URI="http://idugalicmicroservices.sb04.stations.graphenedb.com:24789"
USERNAME="Idugalic_microservices"
PASSWORD="mlzkAME4f85viYEwgSts"
cf cups recommendations-db -p '{"neo4jUri":"http://idugalicmicroservices.sb04.stations.graphenedb.com:24789/db/data/","neo4jUsername":"Idugalic_microservices","neo4jPassword":"mlzkAME4f85viYEwgSts"}'