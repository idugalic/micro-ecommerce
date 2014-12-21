#!/bin/bash
ROUTE=${ROUTE:-localhost:8082}
curl ${ROUTE}/recommendations/people -X POST -d '{"userName":"mstine","firstName":"Matt","lastName":"Stine"}' -H "Content-Type: application/json"
curl ${ROUTE}/recommendations/people -X POST -d '{"userName":"starbuxman","firstName":"Josh","lastName":"Long"}' -H "Content-Type: application/json"
curl ${ROUTE}/recommendations/people -X POST -d '{"userName":"littleidea","firstName":"Andrew","lastName":"Shafer"}' -H "Content-Type: application/json"