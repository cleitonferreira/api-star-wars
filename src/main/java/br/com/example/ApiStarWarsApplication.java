package br.com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.example.model.Planet;
import br.com.example.services.SwapiPlanets;
import br.com.example.services.SwapiServices;

@SpringBootApplication
public class ApiStarWarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiStarWarsApplication.class, args);
		
		SwapiServices swapiServices = new SwapiServices();

		// fetches the planets from SWAPI
		System.out.println("Popular no BD os planetas da SWAPI");
		SwapiPlanets listSwapiPlanets = swapiServices.populateSWPlanets();

		// Adicionar um planeta (com nome, clima e terreno)
		System.out.println("\n\n -- Add um novo planeta");
		Planet planet = new Planet();
		planet.setId(null);
		planet.setSwid("0");
		planet.setName("Teste");
		planet.setClimate("Tropical");
		planet.setTerrain("Plano");
		planet.setFilms(0);
		System.out.println(swapiServices.insertPlanet(planet));
		
		// Listar planetas do banco de dados
		System.out.println("\n\n--Listar planetas do banco de dados--");
		System.out.println(swapiServices.getPlanetList());
		
		// Listar planetas da API do Star Wars
		System.out.println("\n\n--Listar planetas da API do Star Wars--");
		System.out.println(listSwapiPlanets.toString());
		
		// Buscar por nome no banco de dados
		Planet planetByName = swapiServices.getPlanetByName("Corellia");
		System.out.println("\n\n--Buscar por nome no banco de dados--");
		System.out.println(planetByName.toString());
		
		// Buscar por ID no banco de dados
		System.out.println("\n\n--Buscar por ID no banco de dados--");
		System.out.println(swapiServices.getSwPlanets("30"));

		// Remover planeta
		System.out.println("\n\n--Remover planeta--"+planetByName.getId().toString());
		swapiServices.deletePlanetById(planetByName.getId().toString());
	
		
	}
	
	

}
