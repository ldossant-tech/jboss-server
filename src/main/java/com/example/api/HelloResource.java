package com.example.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Hello")
@Path("/hello")
public class HelloResource {

  @Operation(summary = "Mensagem simples", description = "Retorna uma mensagem de boas-vindas")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response hello() {
    Map<String, Object> body = new HashMap<>();
    body.put("message", "Hello from JBoss/WildFly with Swagger!");
    return Response.ok(body).build();
  }
}
