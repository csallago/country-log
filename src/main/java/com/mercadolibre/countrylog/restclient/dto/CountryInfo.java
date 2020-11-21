package com.mercadolibre.countrylog.restclient.dto;

import java.util.List;

import com.mercadolibre.countrylog.domain.Language;

public class CountryInfo {
    private String name;
    private String alpha2Code;
    private String alpha3Code;
    private List<String> timezones;
    private List<Double> latlng;
    private List<Currency> currencies;
    private List<Language> languages;

    public CountryInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }

    public List<Double> getLatlng() {
        return latlng;
    }

    public void setLatlng(List<Double> latlng) {
        this.latlng = latlng;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    

}
