package br.com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import br.com.example.services.SwapiPlanet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@Table("planet")
public class Planet{
	
	@Id
    private Integer id;
    private String swid;
    private String name;
    private String climate;
    private String terrain;
    private Integer films;

    public Planet(){
    }

    public static Planet clonePlanet(Planet p){

        Planet copy = new Planet();

        // copy other properties
        copy.id = p.id;
        copy.swid = p.swid;
        copy.name = p.name;
        copy.climate = p.climate;
        copy.terrain = p.terrain;
        copy.films = p.films;

        return copy;
    }

    public static Planet fromSWPlanet(SwapiPlanet swplanet){
        Planet p = new Planet();
        p.id = null;
        p.swid = swplanet.getId();
        p.name = swplanet.getName();
        p.climate = swplanet.getClimate();
        p.terrain = swplanet.getTerrain();
        p.films = swplanet.getFilms().size();

        return p;
    }


}