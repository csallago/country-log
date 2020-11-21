package com.mercadolibre.countrylog.service;

import com.mercadolibre.countrylog.domain.Country;

public interface CountryInfoService {
    public Country getCountryInfoByIp(String ip);
}
