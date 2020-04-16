package com.capgemini.example.demo.connectors;

import com.capgemini.example.demo.models.DateTimeModel;
import com.capgemini.example.demo.models.WorldTimeAPIResponseModel;

public interface TimeEndpoint {

    WorldTimeAPIResponseModel getTimeFromEndpoint();
}
