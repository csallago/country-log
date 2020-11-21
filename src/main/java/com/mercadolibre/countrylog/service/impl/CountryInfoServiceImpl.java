package com.mercadolibre.countrylog.service.impl;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mercadolibre.countrylog.domain.Country;
import com.mercadolibre.countrylog.domain.LocalTime;
import com.mercadolibre.countrylog.domain.USDCurrency;
import com.mercadolibre.countrylog.restclient.CountryInfoRestClient;
import com.mercadolibre.countrylog.restclient.dto.CountryInfo;
import com.mercadolibre.countrylog.restclient.dto.Currency;
import com.mercadolibre.countrylog.service.CountryInfoService;

import org.springframework.stereotype.Service;

@Service
public class CountryInfoServiceImpl implements CountryInfoService{

    private static double BA_LAT = -34d;
    private static double BA_LONG = -64d;

    CountryInfoRestClient infoRestClient;

    public CountryInfoServiceImpl(CountryInfoRestClient infoRestClient) {
        this.infoRestClient = infoRestClient;
    }
    
    public Country getCountryInfoByIp(String ip) {
        
        System.out.println("llego la ip: "+ip);
        if (!isValidIP(ip))
            throw new RuntimeException("Param ip Malformed");
        
        String countryCode = infoRestClient.getCountryCodeByIp(ip).getCountryCode();
        CountryInfo countryInfo = infoRestClient.getCountryInfoByCode(countryCode);
        
        Country  country = new Country(countryInfo.getName(), 
                            countryInfo.getAlpha2Code(), 
                            countryInfo.getLanguages(),
                            getLocalTimesToTz(countryInfo.getTimezones()),
                            getCurrenciesToUSD(countryInfo.getCurrencies()),
                            getDistanceToBA(countryInfo.getLatlng().get(0),countryInfo.getLatlng().get(1)));

        return country;
    }

    private boolean isValidIP(String ip){

        if (ip == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
        Matcher matcher = pattern.matcher(ip);
        return matcher.find();

    }

    private List<LocalTime> getLocalTimesToTz(List<String> tz) {

        List<LocalTime> localTimes = new ArrayList<>();
        
        for (String zone : tz) {
            String offSet = zone.length() == 3 ? "Z" : zone.substring(3);
            localTimes.add(new LocalTime(Instant.now().atOffset(ZoneOffset.of(offSet))
                                    .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)).toString(), zone));
        }                           
        return localTimes;
    }

    private List<USDCurrency> getCurrenciesToUSD (List<Currency> currencies) {
        List<USDCurrency> usdCurrencies = new ArrayList<>();

        for (Currency usdCurrency : currencies)
            usdCurrencies.add(new USDCurrency(usdCurrency.getCode(), infoRestClient.getExchangeRateToUSD(usdCurrency.getCode())));
        return usdCurrencies;
    }

    private Double getDistanceToBA(Double latFrom, Double longFrom) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(latFrom - BA_LAT);
        double lonDistance = Math.toRadians(longFrom - BA_LONG);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(BA_LAT)) * Math.cos(Math.toRadians(latFrom))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return BigDecimal.valueOf(distance).setScale(2, RoundingMode.FLOOR).doubleValue();
    
    }
}
