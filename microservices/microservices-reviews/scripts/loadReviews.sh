#!/bin/bash
ROUTE=${ROUTE:-localhost:8081}
curl ${ROUTE}/reviews -X POST -d '{"userName":"idugalic","productId":"1","name":"Black Shirt","review":"Great!","rating":"5"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"idugalic","productId":"2","name":"Red Shirt","review":"Pretty good...","rating":"3"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"odugalic","productId":"2","name":"Red Shirt","review":"Cooooool!","rating":"5"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"odugalic","productId":"4","name":"Black Hat","review":"Nice","rating":"3" }}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"odugalic","productId":"5","name":"Brown Hat","review":"Nicely done!","rating":"4"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"hdugalic","productId":"2","name":"Red Shirt","review":"Good show!","rating":"4"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"hdugalic","productId":"3","name":"Green Shirt","review":"Could have been better...","rating":"3"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"jdugalic","productId":"5","name":"Brown Hat","review":"Nicely done!","rating":"4"}' -H "Content-Type: application/json"