#!/bin/bash
ROUTE=${ROUTE:-localhost:8082}
curl ${ROUTE}/products -X POST -d '{"productId":"1","name":"Black Shirt"}' -H "Content-Type: application/json"
curl ${ROUTE}/products -X POST -d '{"productId":"2","name":"Red Shirt"}' -H "Content-Type: application/json"
curl ${ROUTE}/products -X POST -d '{"productId":"3","name":"Green Shirt"}' -H "Content-Type: application/json"
curl ${ROUTE}/products -X POST -d '{"productId":"4","name":"Black Hat"}' -H "Content-Type: application/json"
curl ${ROUTE}/products -X POST -d '{"productId":"5","name":"Brown Hat"}' -H "Content-Type: application/json"