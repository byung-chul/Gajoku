package com.sw.capstone.gajoku;

import com.sw.capstone.gajoku.http.RequestHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Restaurant {
    String name;
    float distance;
    int id;
    int least;

    Restaurant(String name, float distance, int id, int least) {
        this.name = name;
        this.distance = distance;
        this.id = id;
        this.least = least;
    }

    // detail of restaurant information
    String address;
    String phone;
    ArrayList<Menu> menu;
    String detail;

    Restaurant(int id){
        this.id = id;
        menu = new ArrayList<>();

        Url_GetRestaurantInfo url_getRestaurantInfo = new Url_GetRestaurantInfo(id);
        String request = url_getRestaurantInfo.getApiUrl();

        RequestHttp requestHttp = new RequestHttp();
        String response = requestHttp.requestGet(request);

        try {
            JSONArray restaurantResponse = new JSONArray(response);
            JSONObject restaurantDetail = restaurantResponse.getJSONObject(0);
            name = restaurantDetail.getString("restaurantName");
            phone = restaurantDetail.getString("restaurantNumber");
            address = restaurantDetail.getString("restaurantAddress");
            detail = restaurantDetail.getString("restaurantDetail");
            least = restaurantDetail.getInt("restaurantLeast");

            String menuListString = restaurantDetail.getString("restaurantMenu");
            JSONArray menuList = new JSONArray(menuListString);
            System.out.println(menuList.length());
            for(int i = 0; i < menuList.length(); i++){
                JSONObject menuInfo = menuList.getJSONObject(i);
                String name = menuInfo.getString("menu");
                int price = Integer.parseInt(menuInfo.getString("cost"));
                menu.add(new Menu(name, price));
            }
        } catch (JSONException e){
            System.out.println("JSON parse error. / detail restaurant information");
        }
    }
}