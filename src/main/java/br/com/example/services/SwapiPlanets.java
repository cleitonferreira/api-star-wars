package br.com.example.services;

import java.util.Collection;

import lombok.Data;

@Data
public class SwapiPlanets {
    private Integer count;
    private String next; // next URL
    private String previous; // previous URL
    private Collection<SwapiPlanet> results;
    
}