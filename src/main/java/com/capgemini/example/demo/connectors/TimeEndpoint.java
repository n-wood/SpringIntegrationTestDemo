package com.capgemini.example.demo.connectors;

import com.capgemini.example.demo.models.WorldTimeAPIResponseModel;
import reactor.core.publisher.Mono;

public interface TimeEndpoint {

    WorldTimeAPIResponseModel getTimeFromEndpoint();

    Mono<WorldTimeAPIResponseModel> getTimeFromEndpointAsync();
}
