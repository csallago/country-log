package com.mercadolibre.countrylog.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryDistance {
    private String name;
    private Double distance;

    public CountryDistance(String name, Double distance) {
        this.name = name;
        this.distance = distance;
    }

    @JsonProperty("country_name")
    public String getName() {
        return name;
    }

    public Double getDistance() {
        return distance;
    }
}
