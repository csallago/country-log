package com.mercadolibre.countrylog.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {
  
    private String name;
    @JsonProperty("iso_code")
    private String isoCode;
    private List<Language> languages;
    private List<LocalTime> timezones;
    private List<USDCurrency> rates;
    private Double distance;
    
    public Country(){}

    public Country(String name, String isoCode, List<Language> languages, List<LocalTime> timezones,
        List<USDCurrency> rates, Double distance) {
      this.name = name;
      this.isoCode = isoCode;
      this.languages = languages;
      this.timezones = timezones;
      this.rates = rates;
      this.distance = distance;
    }
    
    @JsonProperty("country_name")
    public String getName() {
      return name;
    }

    public String getIsoCode() {
      return isoCode;
    }

    @JsonProperty("local_date")
    public String getLocalDate() {
      return LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
    }

    public List<Language> getLanguages() {
      return languages;
    }

    public List<LocalTime> getTimezones() {
      return timezones;
    }

    @JsonProperty("exchange_rates")
    public List<USDCurrency> getRates() {
      return rates;
    }

    @JsonProperty("distance_to_ba")
    public Double getDistance() {
      return distance;
    }
}
