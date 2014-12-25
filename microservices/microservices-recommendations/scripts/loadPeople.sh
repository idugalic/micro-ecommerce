#!/bin/bash
ROUTE=${ROUTE:-localhost:8082}
curl ${ROUTE}/persons -X POST -d '{"userName":"idugalic","firstName":"Ivan","lastName":"Dugalic"}' -H "Content-Type: application/json"
curl ${ROUTE}/persons -X POST -d '{"userName":"odugalic","firstName":"Olja","lastName":"Dugalic"}' -H "Content-Type: application/json"
curl ${ROUTE}/persons -X POST -d '{"userName":"hdugalic","firstName":"Hana","lastName":"Dugalic"}' -H "Content-Type: application/json"