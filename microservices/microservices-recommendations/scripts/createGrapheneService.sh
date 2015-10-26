#!/bin/bash
URI="http://idugalicmicroservices.sb04.stations.graphenedb.com:24789/db/data/"
USERNAME="Idugalic_microservices"
PASSWORD="mlzkAME4f85viYEwgSts"
cf cups recommendations-dba -p '{"neo4jUri":"${URI}","neo4jUsername":"${USERNAME}","neo4jPassword":"${PASSWORD}"}'