package com.snottis.example.grpcservice;

import org.lognet.springboot.grpc.GRpcService;
import org.lognet.springboot.grpc.recovery.GRpcExceptionHandler;
import org.lognet.springboot.grpc.recovery.GRpcExceptionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Profile;

import io.grpc.Status;
import com.snottis.example.grpcservice.country.model.*;
import com.snottis.example.grpcservice.country.model.CountryProto.Countries;
import com.snottis.example.grpcservice.country.model.CountryProto.CountriesOrBuilder;
import com.snottis.example.grpcservice.country.model.CountryProto.Country;
import com.snottis.example.grpcservice.country.model.CountryProto.FindByNameRequest;
import com.snottis.example.grpcservice.country.model.CountryProto.FindByRequest;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@GRpcService
@Slf4j
public class ReactiveCountryGrpcService extends ReactorCountryServiceGrpc.CountryServiceImplBase {

    @Autowired
    private ReactiveCountryService reactiveCountryService;

    @Override
    public Mono<Country> findCountryByName(Mono<FindByNameRequest> request) {
        return reactiveCountryService.getByName(request).switchIfEmpty(Mono.defer(() -> {
            Status status = Status.NOT_FOUND.withDescription("Country not found");
            return Mono.error(status.asRuntimeException());
        }));
    }

    @Override
    public Mono<Countries> findByGDP(
            Mono<FindByRequest> request) {

        return reactiveCountryService.FindByMinMaxGdp(request).collectList().map(l -> {
            Countries.Builder countries = Countries.newBuilder();
            countries.addAllCountry(l);
            return countries.build();
        }).switchIfEmpty(Mono.defer(() -> {
            Status status = Status.NOT_FOUND.withDescription("Countries not found");
            return Mono.error(status.asRuntimeException());
        }));
    }

    @GRpcExceptionHandler
    public Status handle(Exception ex, GRpcExceptionScope scope) {
        var status = Status.INVALID_ARGUMENT.withDescription(ex.getLocalizedMessage()).withCause(ex);
        log.error("(GrpcExceptionAdvice) : ", ex);
        return status;
    }

}