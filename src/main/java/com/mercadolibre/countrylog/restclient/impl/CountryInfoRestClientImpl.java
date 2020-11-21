package com.mercadolibre.countrylog.restclient.impl;

import com.mercadolibre.countrylog.exception.IPNotFoundException;
import com.mercadolibre.countrylog.exception.ServiceNotAvailableException;
import com.mercadolibre.countrylog.restclient.CountryInfoRestClient;
import com.mercadolibre.countrylog.restclient.dto.CountryInfo;
import com.mercadolibre.countrylog.restclient.dto.ExchangeRates;
import com.mercadolibre.countrylog.restclient.dto.Ip2Country;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CountryInfoRestClientImpl implements CountryInfoRestClient {

    private static final String URL_IP2COUNTRY = "https://api.ip2country.info/ip?%s";
    private static final String URL_RESTCOUNTRY = "https://restcountries.eu/rest/v2/alpha/%s";
    private static final String URL_EXCHANGE_RATES_API = "https://api.exchangeratesapi.io/latest?base=%s";
    private static final String USD_CODE = "USD";
    
    @Override
    public Ip2Country getCountryCodeByIp(String ip) {
        
        RestTemplate restTemplate = new RestTemplate();
        try {
            Ip2Country response = restTemplate.getForObject(String.format(URL_IP2COUNTRY, ip), Ip2Country.class);
            if (response.getCountryCode().isEmpty())
                throw new IPNotFoundException("IP: "+ip+" Not Found");

            return response;

        } catch (Exception e) {
            throw new ServiceNotAvailableException(e.getMessage());
        } 
    }
    
    @Override
    public CountryInfo getCountryInfoByCode(String code) {
        
        RestTemplate restTemplate = new RestTemplate();
        try {
            CountryInfo response = restTemplate.getForObject(String.format(URL_RESTCOUNTRY, code), CountryInfo.class);
            return response;
        } catch (Exception e) {
            throw new ServiceNotAvailableException(e.getMessage());
        }
    }

    public Double getExchangeRateToUSD(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Double usdExchange = null;
        try {
            ExchangeRates rates = restTemplate.getForObject(String.format(URL_EXCHANGE_RATES_API, code), ExchangeRates.class);
            usdExchange = rates.getRates().get(USD_CODE);
        } catch (Exception e) {
            usdExchange = Double.NaN;
        }
        
        return usdExchange;
    }
}
