package com.mercadolibre.countrylog.restclient.dto;

import java.util.Map;

public class ExchangeRates {
    private String base;
    private Map<String, Double> rates;

    public ExchangeRates() {
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
