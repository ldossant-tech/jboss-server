package com.example.api;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> classes = new HashSet<>();
    // Recursos da aplicação
    classes.add(HealthResource.class);
    classes.add(HelloResource.class);
    classes.add(DbResource.class);
    // Recurso do Swagger/OpenAPI
    classes.add(OpenApiResource.class); // expõe /api/openapi
    return classes;
  }
}
