package com.capgemini.example.demo.services;

import com.capgemini.example.demo.models.DateTimeModel;
import reactor.core.publisher.Mono;

public interface TimeService {

    DateTimeModel getServerTime();

    Mono<DateTimeModel> getServerTimeAsync();

}
