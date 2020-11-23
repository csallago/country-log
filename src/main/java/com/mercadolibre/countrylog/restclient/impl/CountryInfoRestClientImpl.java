package com.mercadolibre.countrylog.restclient.impl;

import java.util.Optional;

import com.mercadolibre.countrylog.exception.IPNotFoundException;
import com.mercadolibre.countrylog.exception.ServiceNotAvailableException;
import com.mercadolibre.countrylog.repository.CountryInfoRepository;
import com.mercadolibre.countrylog.restclient.CountryInfoRestClient;
import com.mercadolibre.countrylog.restclient.dto.CountryInfo;
import com.mercadolibre.countrylog.restclient.dto.ExchangeRates;
import com.mercadolibre.countrylog.restclient.dto.Ip2Country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CountryInfoRestClientImpl implements CountryInfoRestClient {

    private static final String URL_IP2COUNTRY = "https://api.ip2country.info/ip?%s";
    private static final String URL_RESTCOUNTRY = "https://restcountries.eu/rest/v2/alpha/%s";
    private static final String URL_EXCHANGE_RATES_API = "https://api.exchangeratesapi.io/latest?base=%s";
    private static final String USD_CODE = "USD";
    
    private CountryInfoRepository countryInfoRepo;
    @Autowired
    private RestTemplate restTemplate;

    public CountryInfoRestClientImpl(CountryInfoRepository countryInfoRepo,
                                        RestTemplate restTemplate) {
        this.countryInfoRepo = countryInfoRepo;
        this.restTemplate = restTemplate;
    }

    @Override
    public Ip2Country getCountryCodeByIp(String ip) {
        
        Ip2Country response = null;
        try {
            response = restTemplate.getForObject(String.format(URL_IP2COUNTRY, ip), Ip2Country.class);
        } catch (Exception e) {
            throw new ServiceNotAvailableException(e.getMessage());
        } 
        if (response.getCountryCode().isEmpty())
            throw new IPNotFoundException("IP: "+ip+" Not Found");

        return response;
    }
    
    @Override
    public CountryInfo getCountryInfoByCode(String code) {
        Optional<CountryInfo> op = countryInfoRepo.findById(code);
    
        if (op.isPresent())
            return op.get();
        
        try {
            CountryInfo response = restTemplate.getForObject(String.format(URL_RESTCOUNTRY, code), CountryInfo.class);
            countryInfoRepo.save(response);
            return response;
        } catch (Exception e) {
            throw new ServiceNotAvailableException(e.getMessage());
        }
    }

    public Double getExchangeRateToUSD(String code) {
        Double usdExchange = null;
        try {
            ExchangeRates rates = restTemplate.getForObject(String.format(URL_EXCHANGE_RATES_API, code), ExchangeRates.class);
            usdExchange = rates.getRates().get(USD_CODE);
            if (usdExchange == null)
                usdExchange = Double.NaN;
        } catch (Exception e) {
            usdExchange = Double.NaN;
        }
        
        return usdExchange;
    }
}
