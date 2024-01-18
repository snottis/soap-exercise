package com.snottis.exercise.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class CountriesRouter {
    @Bean
    public RouterFunction<ServerResponse> route(CountriesHandler countriesHandler) {
        return RouterFunctions.route(GET("/hello").and(accept(MediaType.APPLICATION_JSON)), countriesHandler::hello)
                .andRoute(GET("/finland").and(accept(MediaType.APPLICATION_JSON)), countriesHandler::findFinland);
    }
}
