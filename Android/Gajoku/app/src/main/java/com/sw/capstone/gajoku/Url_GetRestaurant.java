package com.sw.capstone.gajoku;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Url_GetRestaurant {
    private final String url = "http://gajoku-env-1.remcewpict.ap-northeast-2.elasticbeanstalk.com/restaurant?";

    private String u_address;
    private String r_type;

    Url_GetRestaurant(String address, String type){
        u_address = address;
        r_type = type;
    }

    public String getApiUrl(){
        String apiUrl = url;
        apiUrl += "u_address=" + u_address;
        apiUrl += "&r_type=" + r_type;

        return apiUrl;
    }

    public ArrayList<Restaurant> parseJSON(String response){
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        try {
            JSONArray restaurantInfoList = new JSONArray(response);
            for(int i = 0; i < restaurantInfoList.length(); i++) {
                JSONObject restaurantInfo = restaurantInfoList.getJSONObject(i);

                String name = restaurantInfo.getString("restaurantName");
                float distance = Float.parseFloat(restaurantInfo.getString("distance"));
                int id = Integer.parseInt(restaurantInfo.getString("restaurantId"));
                int least = Integer.parseInt(restaurantInfo.getString("restaurantLeast"));
                restaurants.add(new Restaurant(name, distance, id, least));
            }
        } catch (JSONException e){
            System.out.println("JSON parse error. / restaurant");
        }

        return restaurants;
    }
}
