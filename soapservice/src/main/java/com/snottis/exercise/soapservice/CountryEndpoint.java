package com.snottis.exercise.soapservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import countries.Country;
import countries.FindCountriesRequest;
import countries.FindCountriesResponse;

import countries.GetCountryRequest;
import countries.GetCountryResponse;
import java.util.List;
import java.util.Optional;

@Endpoint
public class CountryEndpoint {
    private static final String NAMESPACE = "countries";

    @Autowired
    private CountryRepository countryRepository;

    @PayloadRoot(namespace = NAMESPACE, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.getCountry().add(countryRepository.findCountryByName(request.getName()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "findCountries")
    @ResponsePayload
    public FindCountriesResponse findCountries(@RequestPayload FindCountriesRequest request) {
        FindCountriesResponse response = new FindCountriesResponse();
        List<Country> countryList = response.getCountry();
        System.out.println(request);
        countryRepository
                .findCountries(Optional.ofNullable(request.getName()),
                        Optional.ofNullable(request.getMinPopulation()),
                        Optional.ofNullable(request.getMaxPopulation()),
                        Optional.ofNullable(request.getCurrency()))
                .stream()
                .forEach(country -> countryList.add(country));

        return response;
    }
}
