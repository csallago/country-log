package com.mercadolibre.countrylog.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class USDCurrency {
    private String code;
    private Double usdExchange;

    public USDCurrency(String code, Double usdExchange) {
        this.code = code;
        this.usdExchange = usdExchange;
    }

    @JsonProperty("currency_code")
    public String getCode() {
        return code;
    }
    
    @JsonProperty("usd_exchange")
    public Double getUsdExchange() {
        return usdExchange;
    }
}
