package com.example.daeha.badalapplication;

public class Url_Reject {
    private final String url = "http://gajoku-env-1.remcewpict.ap-northeast-2.elasticbeanstalk.com/delivery/reject?";
    private Integer deliveryId;

    Url_Reject(Integer deliveryId){
        this.deliveryId = deliveryId;
    }

    public String getApiUrl(){
        String apiUrl = url;
        apiUrl += "delivery_id=" + deliveryId;

        return apiUrl;
    }
}
