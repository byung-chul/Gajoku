package com.example.daeha.badalapplication;

public class Url_Login {
    private final String url = "http://gajoku-env-1.remcewpict.ap-northeast-2.elasticbeanstalk.com/login?";
    private String id;
    private String password;

    Url_Login(String id, String password){
        this.id = id;
        this.password = password;
    }

    public String getApiUrl(){
        String apiUrl = url;
        apiUrl += "id=" + id;
        apiUrl += "&password=" + password;

        return apiUrl;
    }
}
