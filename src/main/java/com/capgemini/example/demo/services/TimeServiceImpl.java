package com.capgemini.example.demo.services;

import com.capgemini.example.demo.connectors.TimeEndpoint;
import com.capgemini.example.demo.models.DateTimeModel;
import com.capgemini.example.demo.models.WorldTimeAPIResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class TimeServiceImpl implements TimeService {

    @Autowired
    TimeEndpoint connector;
    private Object WorldTimeAPIResponseModel;

    @Override
    public DateTimeModel getServerTime() {
        WorldTimeAPIResponseModel response = connector.getTimeFromEndpoint();
        return new DateTimeModel(
                new Date(response.getUnixtime() * 1000).toString(),
                response.getClient_ip());
    }

    @Async
    public Mono<DateTimeModel> getServerTimeAsync()  {
        Mono<WorldTimeAPIResponseModel> response = connector.getTimeFromEndpointAsync();
        return response.map(result-> new DateTimeModel(
                new Date(result.getUnixtime() * 1000).toString(),
                result.getClient_ip()));





    }
}
