package com.mercadolibre.countrylog.restclient;

import com.mercadolibre.countrylog.restclient.dto.CountryInfo;
import com.mercadolibre.countrylog.restclient.dto.Ip2Country;

public interface CountryInfoRestClient {
    public Ip2Country getCountryCodeByIp(String ip);
    public CountryInfo getCountryInfoByCode(String code);
    public Double getExchangeRateToUSD(String code);
}
