Request OAuth authorization:

$ curl -X POST -vu client-demo:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=admin&username=admin&grant_type=password&scope=write&client_secret=123456&client_id=client-demo"


Use the access_token returned in the previous request to make the authorized request to the protected endpoint:

$ curl http://localhost:8080 -H "Authorization: Bearer <INSERT TOKEN>"