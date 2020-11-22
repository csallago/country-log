package com.mercadolibre.countrylog.repository;

import com.mercadolibre.countrylog.restclient.dto.CountryInfo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryInfoRepository extends CrudRepository<CountryInfo, String> {
}
