package com.mercadolibre.countrylog.domain;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("Stat")
public class Stat implements Serializable{
    //@Indexed
    private String id; //farestba, nearestba, avg??
    private String value;
    private String countryName;

    public Stat() {}

    public Stat(String id, String value, String countryName) {
        this.id = id;
        this.value = value;
        this.countryName = countryName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    

}
