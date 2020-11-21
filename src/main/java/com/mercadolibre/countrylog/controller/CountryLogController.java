package com.mercadolibre.countrylog.controller;

import com.mercadolibre.countrylog.domain.Country;
import com.mercadolibre.countrylog.service.CountryInfoService;

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

    @GetMapping("/{ip}")
    public Country getCountryInfo(@PathVariable String ip){
        return infoService.getCountryInfoByIp(ip);
    }

}
