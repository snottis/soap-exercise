package com.snottis.example.grpcservice;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Profile;

import com.snottis.example.grpcservice.country.model.*;

@GRpcService
@Profile("!reactive-buggy-security")
public class ReactiveCountryServiceGrpc extends ReactorCountryServiceGrpc.CountryServiceImplBase {

}