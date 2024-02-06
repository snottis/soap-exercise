package com.snottis.exercise.backend.controller;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.snottis.exercise.backend.BackendApplication;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BackendApplication.class, properties = {
        "soap.service.url=http://localhost:9999/ws" }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class CountryControllerTests {
    @LocalServerPort
    int port;

    @Value("${soap.service.url}")
    String soapUrl;

    private WireMockServer server;

    TestRestTemplate testRestTemplate = new TestRestTemplate();

    @BeforeAll
    public void setup() throws MalformedURLException {
        int port = new URL(soapUrl).getPort();
        server = new WireMockServer(wireMockConfig().port(port));
        WireMock.configureFor(port);
        server.start();

    }

    @Test
    @DisplayName("Succesful get request should return country succesfully")
    void getSuccessShouldReturnSuccesfulCountry() throws Exception {
        stubFor(get(urlEqualTo("country/Testcountry"))
                .withHeader("Content-Type", WireMock.containing("xml"))
                .withRequestBody(WireMock.containing("Testcountry"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("GetCountryRequest_Testcountry.xml")));

        assertThat(this.testRestTemplate.getForObject("http://localhost:" + this.port + "/country/Testcountry",
                String.class).contains("Testcapital"));
    }
}
