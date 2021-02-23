package com.capgemini.example.demo.connectors;

import com.capgemini.example.demo.models.WorldTimeAPIResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
public class TimeEndpointImpl implements TimeEndpoint {

    //private String configuredApiEndpointUrl = "http://worldtimeapi.org/api/ip";
    private String configuredApiEndpointUrl = "http://localhost:8081/mock/example";
    @Autowired
    RestTemplate restTemplate;


    @Override
    public WorldTimeAPIResponseModel getTimeFromEndpoint() {
        return restTemplate.getForObject(configuredApiEndpointUrl, WorldTimeAPIResponseModel.class);
    }

    @Override
    public Mono<WorldTimeAPIResponseModel> getTimeFromEndpointAsync() {


        Mono<WorldTimeAPIResponseModel> client = WebClient.create()
                .get()
                .uri(configuredApiEndpointUrl)
                .retrieve()
                .bodyToMono(WorldTimeAPIResponseModel.class);
        client.subscribe();

        return client;


    }
}
