package com.mercadolibre.countrylog.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocalTime {
    private String localTime;
    private String zone;

    public LocalTime(String time, String timezone) {
        this.localTime = time;
        this.zone = timezone;
    }
    @JsonProperty("local_time")
    public String getLocalTime() {
        return localTime;
    }

    public String getZone() {
        return zone;
    }
    
}
