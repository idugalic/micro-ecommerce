#!/bin/bash
ROUTE=${ROUTE:-localhost:8082}
curl ${ROUTE}/movies -X POST -d '{"mlId":"1","title":"Toy Story (1995)"}' -H "Content-Type: application/json"
curl ${ROUTE}/movies -X POST -d '{"mlId":"2","title":"GoldenEye (1995)"}' -H "Content-Type: application/json"
curl ${ROUTE}/movies -X POST -d '{"mlId":"3","title":"Four Rooms (1995)"}' -H "Content-Type: application/json"
curl ${ROUTE}/movies -X POST -d '{"mlId":"4","title":"Get Shorty (1995)"}' -H "Content-Type: application/json"
curl ${ROUTE}/movies -X POST -d '{"mlId":"5","title":"Copycat (1995)"}' -H "Content-Type: application/json"