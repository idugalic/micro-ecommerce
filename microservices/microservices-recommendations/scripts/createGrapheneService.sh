#!/bin/bash
URI="your graphenedb host"
USERNAME="your graphenedb username"
PASSWORD="your graphenedb password"
cf cups recommendations-dba -p '{"neo4jUri":"${URI}","neo4jUsername":"${USERNAME}","neo4jPassword":"${PASSWORD}"}'