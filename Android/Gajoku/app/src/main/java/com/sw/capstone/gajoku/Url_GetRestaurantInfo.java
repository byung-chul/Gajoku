package com.sw.capstone.gajoku;

public class Url_GetRestaurantInfo {
    private final String url = "http://gajoku-env-1.remcewpict.ap-northeast-2.elasticbeanstalk.com/oneRestaurant?";

    private int restaurantId;

    Url_GetRestaurantInfo(int id){
        restaurantId = id;
    }

    public String getApiUrl() {
        String apiUrl = url;
        apiUrl += "r_id=" + restaurantId;

        return apiUrl;
    }
}
