package com.example.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Database")
@Path("/db")
public class DbResource {

  @Resource(lookup = "java:/jdbc/AppDS")
  private DataSource ds;

  @Operation(summary = "Versão do PostgreSQL", description = "Executa SELECT version() usando o DataSource JNDI")
  @GET
  @Path("/version")
  @Produces(MediaType.APPLICATION_JSON)
  public Response version() {
    Map<String, Object> body = new HashMap<>();
    try (Connection c = ds.getConnection();
         Statement st = c.createStatement();
         ResultSet rs = st.executeQuery("select version() as version")) {
      if (rs.next()) {
        body.put("postgres_version", rs.getString("version"));
      }
      body.put("datasource", "java:/jdbc/AppDS");
      body.put("ok", true);
      return Response.ok(body).build();
    } catch (Exception e) {
      body.put("ok", false);
      body.put("error", e.getMessage());
      return Response.status(500).entity(body).build();
    }
  }
}
