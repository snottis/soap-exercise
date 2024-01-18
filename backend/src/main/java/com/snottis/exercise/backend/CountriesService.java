package com.snottis.exercise.backend;

import org.springframework.stereotype.Component;

import com.snottis.exercise.backend.wsdl.CountriesPort;
import com.snottis.exercise.backend.wsdl.CountriesPortService;
import com.snottis.exercise.backend.wsdl.FindCountriesRequest;
import com.snottis.exercise.backend.wsdl.FindCountriesResponse;
import reactor.core.publisher.Mono;

@Component
public class CountriesService {

    CountriesPortService portService = new CountriesPortService();

    public Mono<FindCountriesResponse> findCountries(FindCountriesRequest request) {
        CountriesPort port = portService.getCountriesPortSoap11();
        return Mono.create(sink -> port.findCountriesAsync(request, ReactorAsyncHandler.into(sink)));
    }
}
