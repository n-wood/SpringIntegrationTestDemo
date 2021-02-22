package com.capgemini.example.demo.connectors;

import com.capgemini.example.demo.models.WorldTimeAPIResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
}
