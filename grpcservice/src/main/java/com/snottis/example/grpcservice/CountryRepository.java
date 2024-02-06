package com.snottis.example.grpcservice;

import com.snottis.example.grpcservice.Country;

import reactor.core.publisher.Flux;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CountryRepository extends ReactiveCrudRepository<Country, Long> {

    @Query("SELECT * FROM country WHERE name = :name")
    Flux<Country> findByCountryName(String name);

    @Query("SELECT * FROM country WHERE gdp >= :minGdp AND gdp <= :maxGdp")
    Flux<Country> findByMinMaxGdp(Float minGdp, Float maxGdp);
}