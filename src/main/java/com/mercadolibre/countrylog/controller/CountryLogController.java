package com.mercadolibre.countrylog.controller;

import java.util.HashMap;
import java.util.Map;

import com.mercadolibre.countrylog.domain.Country;
import com.mercadolibre.countrylog.domain.CountryDistance;
import com.mercadolibre.countrylog.service.CountryInfoService;
import com.mercadolibre.countrylog.service.CountryStatsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countrylog")
public class CountryLogController {
    
    @Autowired
    CountryInfoService infoService;

    @Autowired
    CountryStatsService statsService;

    @GetMapping("/{ip}")
    public Country getCountryInfo(@PathVariable String ip){
        Country country = infoService.getCountryInfoByIp(ip);
        statsService.updateStats(country.getDistance(), country.getName());
        return country;
    }

    @GetMapping("/stat/farthest")
    public CountryDistance getFarthestCountry(){
        return statsService.getFarthestCountry();
    }

    @GetMapping("/stat/nearthest")
    public CountryDistance getNearthestCountry(){
        return statsService.getNearthestCountry();
    }

    @GetMapping("/stat/average")
    public Map<String, Object> getAverageDistanceCountry(){
        Map<String, Object> response = new HashMap<>();
        response.put("average_distance_history", statsService.getAverageDistance());
        return response;
    }
}
