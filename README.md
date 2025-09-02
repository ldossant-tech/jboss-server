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
  - WildFly 32+ ou JBoss EAP 8 (Jakarta EE 10)
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

4. **Inicie o servidor**
   ```bash
   sudo /opt/jboss/wildfly/bin/standalone.sh &
   ```
   *Se as portas estiverem em uso, execute:*
   ```bash
    sudo lsof -nP -iTCP:8080 -sTCP:LISTEN
    sudo lsof -nP -iTCP:9990 -sTCP:LISTEN
    sudo kill <PID>
   ```

5. **Configure o driver PostgreSQL e a DataSource**
   Com o servidor em execução, entre no CLI e execute os comandos a seguir
   (ajuste `HOST`, `DB`, `USUARIO` e `SENHA`). Se aparecer
   `WFLYPRT0053: Could not connect` significa que o servidor não está ativo:
   ```bash
    # Substitua DBNAME / DBUSER / DBPASS e ajuste host/porta conforme seu cenário
    /opt/jboss/wildfly/bin/jboss-cli.sh --connect --commands='
    /subsystem=datasources/data-source=AppDS:write-attribute(name=connection-url,value="jdbc:postgresql://127.0.0.1:5432/DBNAME"),
    /subsystem=datasources/data-source=AppDS:write-attribute(name=user-name,value="DBUSER"),
    /subsystem=datasources/data-source=AppDS:write-attribute(name=password,value="DBPASS"),
    :reload,
    /subsystem=datasources/data-source=AppDS:test-connection-in-pool'
   ```

6. **Faça o deploy do WAR**
   ```bash
   sudo cp target/jboss-api-swagger-pg.war /opt/jboss/wildfly/standalone/deployments/
   # ou
   /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="deploy target/jboss-api-swagger-pg.war --force"
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
sudo cp target/jboss-api-swagger-pg.war /opt/jboss/wildfly/standalone/deployments/
# ou via jboss-cli:
# /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="deploy /caminho/jboss-api-swagger-pg.war --force"
```

## Acessos
- Swagger UI: `http://<HOST>:8080/jboss-api-swagger-pg/swagger-ui.html`
- OpenAPI:    `http://<HOST>:8080/jboss-api-swagger-pg/api/openapi`
- Saúde:      `http://<HOST>:8080/jboss-api-swagger-pg/api/health`
- Hello:      `http://<HOST>:8080/jboss-api-swagger-pg/api/hello`
- DB:         `http://<HOST>:8080/jboss-api-swagger-pg/api/db/version`
