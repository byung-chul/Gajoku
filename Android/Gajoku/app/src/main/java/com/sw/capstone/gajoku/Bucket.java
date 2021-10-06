package com.sw.capstone.gajoku;

import org.json.JSONException;
import org.json.JSONObject;

public class Bucket {
    String restaurantName;
    String menu;
    int cost;
    int quantity;

    Bucket(String restaurantName, String menuName, int price, int quantity){
        this.restaurantName = restaurantName;
        this.menu = menuName;
        this.cost = price;
        this.quantity = quantity;
    }

    Bucket(JSONObject bucket_json){
        try {
            this.restaurantName = bucket_json.getString("restaurantName");
            this.menu = bucket_json.getString("menu");
            this.cost = bucket_json.getInt("cost");
            this.quantity = bucket_json.getInt("quantity");
        } catch (JSONException e){
            System.out.println("JSON parse error. / bucket");
        }
    }
}
