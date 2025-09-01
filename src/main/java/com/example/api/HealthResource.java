package com.example.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Health")
@Path("/health")
public class HealthResource {

  @Operation(summary = "Status da aplicação", description = "Retorna UP se o serviço estiver saudável")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response status() {
    Map<String, Object> body = new HashMap<>();
    body.put("status", "UP");
    body.put("service", "jboss-api-swagger-pg");
    return Response.ok(body).build();
  }
}
