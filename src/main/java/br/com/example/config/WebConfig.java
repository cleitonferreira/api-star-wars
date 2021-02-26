package br.com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import br.com.example.resource.PlanetResource;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

  @Bean
  public RouterFunction<ServerResponse> routeShow(PlanetResource planetHandler) {
    return RouterFunctions
      .route(RequestPredicates.GET("/planets/id/{id}"), planetHandler::byId)
      .andRoute(RequestPredicates.GET("/planets/name/{name}"), planetHandler::byName)
      .andRoute(RequestPredicates.GET("/planets/swid/{swid}"), planetHandler::allSw)
      .andRoute(RequestPredicates.GET("/planets"), planetHandler::all)
      .andRoute(RequestPredicates.POST("/planets"), planetHandler::insertPlanet)
      .andRoute(RequestPredicates.DELETE("/planets/id/{id}"), planetHandler::delete);
  }

}