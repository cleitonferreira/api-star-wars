package br.com.example;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.connectionfactory.init.CompositeDatabasePopulator;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;

import br.com.example.repository.PlanetRepository;
import io.r2dbc.spi.ConnectionFactory;
import reactor.test.StepVerifier;

// see: https://github.com/spring-projects-experimental/spring-boot-r2dbc/issues/68
@DataR2dbcTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ActiveProfiles("tc")
public class PlanetRepositoryTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

            ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
            initializer.setConnectionFactory(connectionFactory);

            CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
            //populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
            //populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("data.sql")));
            initializer.setDatabasePopulator(populator);

            return initializer;
        }
    }

    @Autowired
    DatabaseClient client;

    @Autowired
    PlanetRepository planetRepository;

    @Test
    public void testDatabaseClientExisted() {
        assertNotNull(client);
    }

    @Test
    public void testPostRepositoryExisted() {
        assertNotNull(planetRepository);
    }


    @Test
    public void existedOneItemInPlanet() {
        assertThat(this.planetRepository.count().block()).isEqualTo(1);
    }
    

    @Test
    public void testInsertAndQuery() {
        this.client.insert()
                .into("planet")
                //.nullValue("id", Integer.class)
                .value("swid", "999999")
                .value("name", "testname")
                .value("climate", "testclimate")
                .value("terrain", "testterrain")
                .value("films", 0)
                .then().block(Duration.ofSeconds(5));

        this.planetRepository.findByClimate("%temperate")
                .take(1)
                .as(StepVerifier::create)
                .consumeNextWith(p -> assertEquals("temperate", p.getClimate()))
                .verifyComplete();

    }
}