package com.snottis.example.grpcservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snottis.example.grpcservice.country.model.ReactorCountryServiceGrpc;
import com.snottis.example.grpcservice.country.model.CountryProto.FindByNameRequest;
import com.snottis.example.grpcservice.country.model.CountryProto.FindByRequest;
import com.snottis.example.grpcservice.country.model.CountryProto.Country;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveCountryService {
    @Autowired
    CountryRepository countryRepository;

    public Mono<Country> getByName(
            Mono<FindByNameRequest> request) {
        return request.flatMap(r -> countryRepository.findByCountryName(r.getName()).next()
                .map(c -> Country.newBuilder().setName(c.getName()).setGdp(c.getGdp()).build()));
    }

    public Flux<Country> FindByMinMaxGdp(
            Mono<FindByRequest> request) {
        return request.flatMapMany(r -> countryRepository.findByMinMaxGdp(r.getMinimumGdp(), r.getMaximumGdp())
                .map(c -> Country.newBuilder().setName(c.getName()).setGdp(c.getGdp()).build()));
    }
}