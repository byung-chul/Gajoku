package com.sw.capstone.gajoku;

public class Url_GetCurrentOrder {
    private final String url = "http://gajoku-env-1.remcewpict.ap-northeast-2.elasticbeanstalk.com/order/waiting?";

    int r_id;
    String u_address;

    Url_GetCurrentOrder(int r_id, String u_address){
        this.r_id = r_id;
        this.u_address = u_address;
    }

    public String getApiUrl(){
        String apiUrl = url;
        apiUrl += "r_id=" + r_id;
        apiUrl += "&u_address=" + u_address;

        return apiUrl;
    }
}
