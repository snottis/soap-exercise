package com.snottis.example.grpcservice;

import com.snottis.example.grpcservice.Country;

import reactor.core.publisher.Flux;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CountryRepository extends ReactiveCrudRepository<Country, Long> {

    @Query("SELECT * FROM country WHERE country_name = :name")
    Flux<Country> findByCountryName(String name);

    @Query("SELECT * FROM country WHERE "
            + "(:minGdp IS NULL OR gdp >= :minGdp) AND"
            + "(:maxGdp IS NULL OR gdp <= :maxGdp)")
    Flux<Country> findByMinMaxGdp(Float minGdp, Float maxGdp);
}