package com.snottis.exercise.backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.server.ResponseStatusException;

import com.snottis.exercise.backend.wsdl.FindCountriesRequest;
import com.snottis.exercise.backend.wsdl.FindCountriesResponse;

import reactor.core.publisher.Mono;
import com.snottis.exercise.backend.request.SoapEnvelopeRequest;

import javax.xml.parsers.ParserConfigurationException;
import jakarta.xml.soap.SOAPException;
import java.io.IOException;

@Component
public class ReactiveSoapClient {

    private WebClient webClient;
    private String soapServiceUrl;

    public ReactiveSoapClient(WebClient webClient,
            @Value("${soap.service.url}") String soapServiceUrl) {
        this.webClient = webClient;
        this.soapServiceUrl = soapServiceUrl;
    }

    public <T> ResponseSpec call(T request, String soapHeaderContent)
            throws SOAPException, ParserConfigurationException, IOException {
        SoapEnvelopeRequest soapEnvelopeRequest = new SoapEnvelopeRequest(soapHeaderContent, request);

        return webClient.post()
                .uri(soapServiceUrl)
                .contentType(MediaType.TEXT_XML)
                .body(Mono.just(soapEnvelopeRequest), SoapEnvelopeRequest.class)
                .retrieve();
    }

    // public void call(FindCountriesRequest findCountriesRequest, String
    // soapHeaderContent)
    // throws SOAPException, ParserConfigurationException, IOException {

    // SoapEnvelopeRequest soapEnvelopeRequest = new
    // SoapEnvelopeRequest(soapHeaderContent, findCountriesRequest);

    // webClient.post()
    // .uri(soapServiceUrl)
    // .contentType(MediaType.TEXT_XML)
    // .body(Mono.just(soapEnvelopeRequest), SoapEnvelopeRequest.class)
    // .retrieve()
    // .onStatus(
    // status -> status.isError(),
    // clientResponse -> clientResponse
    // .bodyToMono(String.class)
    // .flatMap(
    // errorResponseBody -> Mono.error(
    // new ResponseStatusException(
    // clientResponse.statusCode(),
    // errorResponseBody))))

    // .bodyToMono(FindCountriesResponse.class)
    // .doOnSuccess((FindCountriesResponse response) -> {
    // System.out.println("success");
    // System.out.println("capital : " + response.getCountry());
    // })
    // .doOnError(ResponseStatusException.class, error -> {
    // System.out.println("error : " + error);
    // })
    // .doOnError(Exception.class, (Exception error) -> {
    // System.out.println("error : " + error);
    // error.printStackTrace();
    // })
    // .subscribe();

    // }

}