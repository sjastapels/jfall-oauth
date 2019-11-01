# Client Credentials Demo

## Prepare

Create an authorization server. This demo was created using auth0.

## Run

```cmd
mvn spring-boot:run
```

## Demo

>> No header

```cmd
curl localhost:8080/balance -s | jq .
```

>> Invalid bearer

```cmd
curl localhost:8080/balance -H 'Authorization: Bearer test' -s | jq .
```

>> Expired token

```cmd
curl localhost:8080/balance -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IlJUVTJSVVkwUWpoR056YzNNREJHTlRKR05EQkZSalF6TlRoRU5rTTVOME5ETnpZM056RkNOQSJ9.eyJpc3MiOiJodHRwczovL3NzdGFwZWxzLmV1LmF1dGgwLmNvbS8iLCJzdWIiOiJ4elg3WGY0T1c4MkJUNjJ6VTZXRzJmcEh3QUdDVlo4ZUBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9zc3RhcGVscy5ldS5hdXRoMC5jb20vYXBpL3YyLyIsImlhdCI6MTU3MjIxNDUzMiwiZXhwIjoxNTcyMzAwOTMyLCJhenAiOiJ4elg3WGY0T1c4MkJUNjJ6VTZXRzJmcEh3QUdDVlo4ZSIsInNjb3BlIjoicmVhZDp1c2VycyIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyJ9.yRIsRmkkjGZJK81ATy1JktPWITSceK5AQT-Xh4rlLECEvB6Qjf-x-7_OBKGJF19rHq6W5oSt_nmK3qTQe-QMO6hpLHAnHXOYxX2lcS2uYwPJ8qqEbyggTKpkh3o5C-xOUC1-f0xnDsVFcPc67dNBZkWTsThrs3XwQnbu0zZyYv6a3gjqUq2P_FSMbDYLPWlWrtXjgPiIggRKrjnILp0efdRDtEy5U8q_Phjz4CBiqe6yUYAG49xDKwsUceT9Y2hQFA-N9afCUjJeurS79H3fCfPY6pzEZxlYs1VcTO0YfcBbkB0iQo2KgaL4CnN660HivkPI04FMXZq6hGoo7g2qoA' -s | jq .
```

>> Get Token

```cmd
curl https://<create your own>.eu.auth0.com/oauth/token \
--header 'content-type: application/json' \
--data '{
    "client_id": "<created for you by auth0>",
    "client_secret": "<created for you by auth0>",
    "audience": "https://<as created>.eu.auth0.com/api/v2/",
    "grant_type": "client_credentials",
    "scope": "read:users"
}'
```

>> Valid token

```cmd
curl localhost:8080/balance -H 'Authorization: Bearer <insert valid token>' -s | jq .
```
