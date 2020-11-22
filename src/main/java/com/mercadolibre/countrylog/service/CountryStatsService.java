package com.mercadolibre.countrylog.service;

import com.mercadolibre.countrylog.domain.CountryDistance;

public interface CountryStatsService {
    public CountryDistance getFarthestCountry();
    public CountryDistance getNearthestCountry();
    public Double getAverageDistance();
    public void updateStats(Double distance, String countryName);
}
