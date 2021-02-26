package br.com.example.services;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.example.model.Planet;
import reactor.core.publisher.Mono;

public class SwapiServices {
	
    private WebClient client = WebClient.create("http://localhost:8080");
    WebClient swClient = WebClient.create();

    public SwapiPlanets populateSWPlanets() {
    	return fetchSWPlanetsPage("https://swapi.dev/api/planets/?format=json");
    }

    private SwapiPlanets fetchSWPlanetsPage(String url) {
        Mono<ClientResponse> result = swClient.get().uri(url).accept(MediaType.APPLICATION_JSON).exchange();

        SwapiPlanets swPlanetList = result.flatMap(res -> res.bodyToMono(SwapiPlanets.class)).block();
        
        for (SwapiPlanet swPlanet : swPlanetList.getResults()) {
            this.insertPlanet(Planet.fromSWPlanet(swPlanet));
        }
        
        if (swPlanetList.getNext() != null) {
            fetchSWPlanetsPage(swPlanetList.getNext().replaceAll("http://", "https://"));
        }
        
        return swPlanetList;
    }

    public String getPlanetList() {
        Mono<ClientResponse> result = client.get().uri("planets").accept(MediaType.APPLICATION_JSON).exchange();

        return result.flatMap(res -> res.bodyToMono(String.class)).block();
    }

    public String getSwPlanets(String swid) {
        Mono<ClientResponse> result = client.get().uri("planets/swid/{id}", swid).accept(MediaType.APPLICATION_JSON).exchange();

        return result.flatMap(res -> res.bodyToMono(String.class)).block();
    }

    public String insertPlanet(Planet p) {
        Mono<ClientResponse> result = client.post().uri("planets").body(Mono.just(p), Planet.class)
                .accept(MediaType.APPLICATION_JSON).exchange();
        return result.flatMap(res -> res.bodyToMono(String.class)).block();
    }

    public Planet getPlanetByName(String name) {
        Mono<ClientResponse> result = client.get().uri("/planets/name/{name}", name).accept(MediaType.APPLICATION_JSON)
                .exchange();
        return result.flatMap(res -> res.bodyToMono(Planet.class)).block();
    }

    public void deletePlanetById(String id) {
        client.delete().uri("/planets/id/{id}", id).exchange().map(ClientResponse::statusCode)
                .subscribe(status -> System.out.println("DELETE: " + status));
    }
}