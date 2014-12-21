#!/bin/bash
ROUTE=${ROUTE:-localhost:8081}
curl ${ROUTE}/reviews/reviews -X POST -d '{"userName":"mstine","mlId":"1","title":"Toy Story (1995)","review":"Great movie!","rating":"5"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews/reviews -X POST -d '{"userName":"mstine","mlId":"2","title":"GoldenEye (1995)","review":"Pretty good...","rating":"3"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews/reviews -X POST -d '{"userName":"starbuxman","mlId":"2","title":"GoldenEye (1995)","review":"BOND BOND BOND!","rating":"5"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews/reviews -X POST -d '{"userName":"starbuxman","mlId":"4","title":"Get Shorty (1995)","review":"Meh","rating":"3" }}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews/reviews -X POST -d '{"userName":"starbuxman","mlId":"5","title":"Copycat (1995)","review":"Nicely done!","rating":"4"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews/reviews -X POST -d '{"userName":"littleidea","mlId":"2","title":"GoldenEye (1995)","review":"Good show!","rating":"4"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews/reviews -X POST -d '{"userName":"littleidea","mlId":"3","title":"Four Rooms (1995)","review":"Could have been better...","rating":"3"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews/reviews -X POST -d '{"userName":"littleidea","mlId":"5","title":"Copycat (1995)","review":"Nicely done!","rating":"4"}' -H "Content-Type: application/json"