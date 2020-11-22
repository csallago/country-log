package com.mercadolibre.countrylog.repository;

import com.mercadolibre.countrylog.domain.Stat;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends CrudRepository<Stat, String>{

}