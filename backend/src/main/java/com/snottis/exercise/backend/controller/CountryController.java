package com.snottis.exercise.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.snottis.exercise.backend.client.ReactiveSoapClient;
import com.snottis.exercise.backend.wsdl.GetCountryRequest;
import com.snottis.exercise.backend.wsdl.GetCountryResponse;

import jakarta.xml.soap.SOAPException;
import reactor.core.publisher.Mono;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CountryController {

    private ReactiveSoapClient reactiveSoapClient;

    public CountryController(ReactiveSoapClient reactiveSoapClient) {
        this.reactiveSoapClient = reactiveSoapClient;
    }

    @GetMapping("/country/{country}")
    public Mono<GetCountryResponse> getCountry(@PathVariable String country)
            throws SOAPException, ParserConfigurationException, IOException {
        System.out.println("country : " + country);
        GetCountryRequest request = new GetCountryRequest();
        request.setName(country);
        return reactiveSoapClient.call(request, null).bodyToMono(GetCountryResponse.class);
    }

    // @PostMapping("/country/find")
    // public Mono<ServerResponse> postMethodName(@RequestBody SomeEnityData entity)
    // {
    // // TODO: process POST request

    // return entity;
    // }

}
