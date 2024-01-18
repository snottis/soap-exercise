package com.snottis.exercise.soapservice;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.*;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import countries.Country;
import jakarta.annotation.PostConstruct;

@Component
public class CountryRepository {
    private static final Map<String, Country> countries = new HashMap<>();

    @PostConstruct
    public void initData() {
        Country finland = new Country();
        finland.setName("Finland");
        finland.setCapital("Helsinki");
        finland.setCurrency("EUR");
        finland.setPopulation(5600000);
        countries.put(finland.getName(), finland);

        Country estonia = new Country();
        estonia.setName("Estonia");
        estonia.setCapital("Tallinn");
        estonia.setCurrency("EUR");
        estonia.setPopulation(1300000);
        countries.put(estonia.getName(), estonia);

        Country sweden = new Country();
        sweden.setName("Sweden");
        sweden.setCapital("Stockholm");
        sweden.setCurrency("SEK");
        sweden.setPopulation(10500000);
        countries.put(sweden.getName(), sweden);
    }

    public Country findCountryByName(String name) {
        Assert.notNull(name, "Country's name must not be null");
        return countries.get(name);
    }

    public List<Country> findCountries(Optional<String> name, Optional<Integer> minPopulation,
            Optional<Integer> maxPopulation, Optional<String> currency) {
        Stream<Country> returnCountries = countries.values().stream();
        System.out.println(countries);
        if (name.isPresent()) {
            returnCountries = returnCountries.filter(country -> country.getName().startsWith(name.get()));
        }
        if (minPopulation.isPresent()) {
            returnCountries = returnCountries.filter(country -> minPopulation.get() <= country.getPopulation());
        }
        if (maxPopulation.isPresent()) {
            returnCountries = returnCountries.filter(country -> maxPopulation.get() >= country.getPopulation());
        }
        if (currency.isPresent()) {
            returnCountries = returnCountries.filter(country -> currency.get().equals(country.getCurrency()));
        }
        return returnCountries.toList();
    }
}
