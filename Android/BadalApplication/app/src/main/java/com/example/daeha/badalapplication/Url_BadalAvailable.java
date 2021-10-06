package com.example.daeha.badalapplication;

public class Url_BadalAvailable {
    private final String url = "http://gajoku-env-1.remcewpict.ap-northeast-2.elasticbeanstalk.com/call?";

    String restaurant;
    String address;
    String detail_rest;
    String detail_addr;

    Url_BadalAvailable(){
        this.restaurant = restaurant;
        this.address = address;
        this.detail_rest = detail_rest;
        this.detail_addr = detail_addr;
    }

    public String getApiUrl(){
        String apiUrl = url;

        return apiUrl;
    }
}
