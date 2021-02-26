package br.com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.example.model.Planet;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

    @LocalServerPort
    private int port;

    private WebTestClient webClient;

    @BeforeAll
    public void setup() {
        this.webClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + this.port)
                .build();
    }

    @Test
    public void willLoadPlanet() {
    	this.webClient.get().uri("/planet")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBodyList(Planet.class).hasSize(61);
    }

}

