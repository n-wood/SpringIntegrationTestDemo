package com.capgemini.example.demo.connectors;

import com.capgemini.example.demo.models.WorldTimeAPIResponseModel;

public interface TimeEndpoint {

    WorldTimeAPIResponseModel getTimeFromEndpoint();

    <Mono> reactor.core.publisher.Mono<WorldTimeAPIResponseModel> getTimeFromEndpointAsync();
}
