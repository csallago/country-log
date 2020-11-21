package com.mercadolibre.countrylog.domain;

public class Language {
    private String iso639_1;
    private String name;

    public Language() {
    }

    public String getIso639_1() {
        return iso639_1;
    }

    public void setIso639_1(String iso639_1) {
        this.iso639_1 = iso639_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
