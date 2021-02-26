package br.com.example.resource;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import br.com.example.model.Planet;
import br.com.example.repository.PlanetRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PlanetResource {
  
  private PlanetRepository planetRepository;

  public PlanetResource(PlanetRepository planetRepository) {
	    this.planetRepository = planetRepository;
  }
  

  public Mono<ServerResponse> all(ServerRequest serverRequest) {
    Flux<Planet> planets = this.planetRepository.findAll();
    return ServerResponse.ok().body(planets, Planet.class);
  }

  public Mono<ServerResponse> byId(ServerRequest serverRequest) {
    String id = serverRequest.pathVariable("id");
    Mono<Planet> planet = this.planetRepository.findById(Integer.parseInt(id));  
    return ServerResponse.ok().body(planet, Planet.class);
  }

  public Mono<ServerResponse> byName(ServerRequest serverRequest){
    String name = serverRequest.pathVariable("name");
    Mono<Planet> planet = this.planetRepository.findByName(name);
    return ServerResponse.ok().body(planet, Planet.class);
  }

  public Mono<ServerResponse> allSw(ServerRequest serverRequest){
	Mono<Planet> planets = this.planetRepository.findBySwid(serverRequest.pathVariable("swid"));
    return ServerResponse.ok().body(planets, Planet.class);
  }

  public Mono<ServerResponse> insertPlanet(ServerRequest serverRequest){
	Mono<Planet> planet = serverRequest.bodyToMono(Planet.class);
	return ServerResponse.ok().body(planet.map(p -> p ).flatMap(planetRepository::save), Planet.class);
  }

  public Mono<ServerResponse> delete(ServerRequest serverRequest){
	  planetRepository.deleteById(Integer.parseInt(serverRequest.pathVariable("id")));
    return ServerResponse.ok().build();
  }
  
}