package com.capgemini.example.demo.controllers;

import com.capgemini.example.demo.models.DateTimeModel;
import com.capgemini.example.demo.services.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class DemoController {

    @Autowired
    TimeService service;


    @GetMapping("/what-time-is-it")
    public ResponseEntity<DateTimeModel> handleGetRequest()
    {
       return new ResponseEntity<>( service.getServerTime(), HttpStatus.OK );
    }

    @GetMapping("/what-time-is-it-async")
    public Mono<ResponseEntity<DateTimeModel>> handleAsyncGetRequest()
    {
        return service.getServerTimeAsync().map(
                result-> ResponseEntity.status(HttpStatus.OK).body(result));

    }
}
