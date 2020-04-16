package com.capgemini.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeModel {

    private String dateTime;
    private String ipAddress;

    public DateTimeModel() {

    }

    public DateTimeModel(String dateTime, String ipAddress) {
        this.dateTime = dateTime;
        this.ipAddress = ipAddress;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
