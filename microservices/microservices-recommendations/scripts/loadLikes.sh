#!/bin/bash
ROUTE=${ROUTE:-localhost:8082}
curl -X POST ${ROUTE}/recommendations/mstine/likes/1
curl -X POST ${ROUTE}/recommendations/mstine/likes/2
curl -X POST ${ROUTE}/recommendations/starbuxman/likes/2
curl -X POST ${ROUTE}/recommendations/starbuxman/likes/4
curl -X POST ${ROUTE}/recommendations/starbuxman/likes/5
curl -X POST ${ROUTE}/recommendations/littleidea/likes/2
curl -X POST ${ROUTE}/recommendations/littleidea/likes/3
curl -X POST ${ROUTE}/recommendations/littleidea/likes/5
