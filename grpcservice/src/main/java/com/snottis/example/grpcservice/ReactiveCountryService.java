package com.snottis.example.grpcservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snottis.example.grpcservice.country.model.CountryProto.FindByNameRequest;
import com.snottis.example.grpcservice.country.model.CountryProto.FindByRequest;

import lombok.extern.slf4j.Slf4j;

import com.snottis.example.grpcservice.country.model.CountryProto.Country;

import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ReactiveCountryService {
    @Autowired
    CountryRepository countryRepository;

    public Mono<Country> getByName(
            Mono<FindByNameRequest> request) {
        return Mono.from(request).flatMap(r -> countryRepository.findByCountryName(r.getName()).next()
                .map(c -> {
                    return Country.newBuilder().setName(c.getName()).setGdp(c.getGdp()).build();
                }));
    }

    public Flux<Country> FindByMinMaxGdp(
            Mono<FindByRequest> request) {
        return request.flatMapMany(
                r -> countryRepository
                        .findByMinMaxGdp(r.hasMinimumGdp() ? r.getMinimumGdp() : null,
                                r.hasMaximumGdp() ? r.getMaximumGdp() : null)
                        .map(c -> Country.newBuilder().setName(c.getName()).setGdp(c.getGdp()).build()));
    }
}