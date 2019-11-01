# Authorization Code Demo

## Prepare

Create an OAuth client at github.

### GitHub

1. [https://github.com/settings/developers](https://github.com/settings/developers)
2. New OAuth App
3. Homepage-url: http://localhost:8080
4. Authorization callback URL: http://localhost:8080/login/oauth2/code/github
5. Add credentials to resources/application.yml

## Run

```cmd
mvn spring-boot:run
```

## Demo

```browser
localhost:3002/
```
