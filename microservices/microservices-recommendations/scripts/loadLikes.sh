#!/bin/bash
ROUTE=${ROUTE:-localhost:8082}
curl -X POST ${ROUTE}/recommendations/idugalic/likes/1
curl -X POST ${ROUTE}/recommendations/idugalic/likes/2
curl -X POST ${ROUTE}/recommendations/idugalic/likes/2
curl -X POST ${ROUTE}/recommendations/odugalic/likes/4
curl -X POST ${ROUTE}/recommendations/odugalic/likes/5
curl -X POST ${ROUTE}/recommendations/odugalic/likes/2
curl -X POST ${ROUTE}/recommendations/hdugalic/likes/3
curl -X POST ${ROUTE}/recommendations/hdugalic/likes/5
