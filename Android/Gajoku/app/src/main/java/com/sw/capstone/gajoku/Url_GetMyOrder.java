package com.sw.capstone.gajoku;

public class Url_GetMyOrder {
    private final String url = "http://gajoku-env-1.remcewpict.ap-northeast-2.elasticbeanstalk.com/order/myorder?";

    private String u_id;

    Url_GetMyOrder(String u_id){
        this.u_id = u_id;
    }

    public String getApiUrl() {
        String apiUrl = url;
        apiUrl += "u_id=" + u_id;

        return apiUrl;
    }
}
