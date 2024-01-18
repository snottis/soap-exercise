package com.snottis.exercise.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.snottis.exercise.backend.wsdl.FindCountriesRequest;
import com.snottis.exercise.backend.wsdl.FindCountriesResponse;

import reactor.core.publisher.Mono;

@Component
public class CountriesHandler {

    @Autowired
    CountriesService service;

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("Hello Spring"));
    }

    public Mono<ServerResponse> findFinland(ServerRequest request) {
        FindCountriesRequest req = new FindCountriesRequest();
        req.setName("Finland");
        Mono<FindCountriesResponse> res = service.findCountries(req);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(res, FindCountriesResponse.class));
    }
}
