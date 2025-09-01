# JBoss API com Swagger (OpenAPI) + PostgreSQL (DataSource JNDI)

API JAX‑RS (WAR) para rodar em **JBoss EAP / WildFly** (sem container), com:
- **Swagger/OpenAPI 3** (swagger-core) em `/api/openapi`
- **Swagger UI** em `/swagger-ui.html`
- Endpoint de **saúde** e **hello**
- Endpoint que consulta o **PostgreSQL** via **DataSource JNDI** `java:/jdbc/AppDS`

## Endpoints
- `GET /api/health`
- `GET /api/hello`
- `GET /api/db/version` → testa DataSource e retorna `select version()`
- `GET /api/openapi` → JSON OpenAPI (para o Swagger UI)

## Requisitos no servidor
- JBoss EAP 7.x ou WildFly 26+ (modo standalone)
- Java 11 ou 17
- Driver PostgreSQL registrado como **módulo** e DataSource `java:/jdbc/AppDS` configurado
  (pode ser via CLI — exemplo no seu ambiente atual)

## Build
```bash
mvn -DskipTests package
```
Gera `target/jboss-api-swagger-pg.war`.

## Deploy
```bash
cp target/jboss-api-swagger-pg.war /opt/jboss/wildfly/standalone/deployments/
# ou via jboss-cli:
# /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="deploy /caminho/jboss-api-swagger-pg.war --force"
```

## Acessos
- Swagger UI: `http://<HOST>:8080/jboss-api-swagger-pg/swagger-ui.html`
- OpenAPI:    `http://<HOST>:8080/jboss-api-swagger-pg/api/openapi`
- Saúde:      `http://<HOST>:8080/jboss-api-swagger-pg/api/health`
- Hello:      `http://<HOST>:8080/jboss-api-swagger-pg/api/hello`
- DB:         `http://<HOST>:8080/jboss-api-swagger-pg/api/db/version`
