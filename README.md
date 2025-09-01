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

## Passo a passo para executar

1. **Defina o `JBOSS_HOME`**  
   Aponte a variável para a instalação do servidor ou use o caminho absoluto.
   ```bash
   export JBOSS_HOME=/caminho/para/wildfly
   ```
   O erro `bash: /bin/jboss-cli.sh: Arquivo ou diretório inexistente` ocorre quando a
   variável não está definida corretamente.

2. **Clone e acesse o repositório**  
   ```bash
   git clone <URL-do-repositório>
   cd jboss-server
   ```

3. **Compile o projeto**  
   ```bash
   mvn -DskipTests package
   ```

4. **Configure o driver PostgreSQL e a DataSource**  
   Entre no CLI e execute os comandos a seguir (ajuste `HOST`, `DB`, `USUARIO` e `SENHA`):
   ```bash
   $JBOSS_HOME/bin/jboss-cli.sh
   [disconnected /] connect
   [standalone@localhost:9990 /] /subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql,driver-module-name=org.postgresql,driver-class-name=org.postgresql.Driver)
   [standalone@localhost:9990 /] /subsystem=datasources/data-source=AppDS:add(jndi-name=java:/jdbc/AppDS,driver-name=postgresql,connection-url=jdbc:postgresql://HOST/DB,user-name=USUARIO,password=SENHA)
   [standalone@localhost:9990 /] exit
   ```

5. **Inicie o servidor**  
   ```bash
   $JBOSS_HOME/bin/standalone.sh &
   ```

6. **Faça o deploy do WAR**  
   ```bash
   cp target/jboss-api-swagger-pg.war $JBOSS_HOME/standalone/deployments/
   # ou via CLI:
   $JBOSS_HOME/bin/jboss-cli.sh --connect --command="deploy target/jboss-api-swagger-pg.war --force"
   ```

7. **Acesse a aplicação**  
   Endpoints disponíveis estão listados na seção [Acessos](#acessos).

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
