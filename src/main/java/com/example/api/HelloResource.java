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
