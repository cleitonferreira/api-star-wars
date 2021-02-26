package br.com.example.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.example.model.Planet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlanetRepository extends R2dbcRepository<Planet, Integer> {

	@Query("SELECT * FROM planet WHERE upper(name) like upper(:name)")
	Mono<Planet> findByName(String name);
	
	@Query("SELECT * FROM planet WHERE upper(climate) like upper(:climate)")
	Flux<Planet> findByClimate(String climate);

	@Query("SELECT * FROM planet WHERE swid like :swid")
	Mono<Planet> findBySwid(String swid);
	
	@Transactional @Modifying
	@Query("DELETE FROM planet p WHERE p.id = :id")
    Mono<Void> deletePlanetById(@Param("id") int id);

}
