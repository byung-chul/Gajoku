package com.sw.capstone.gajoku;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyOrder {
    int orderId;
    int matchingId;
    String customerId;
    String restaurantId;
    ArrayList<Bucket> orderContent;
    int orderCost;
    int orderSurplus;
    String customerAddress;
    int orderType;  // 0 = 접수 안됨, 1 = 접수 완료
    String timestamp;
    double customerLat;
    double customerLng;

    MyOrder(JSONObject myOrder_json){
        orderContent = new ArrayList<>();

        try {
            this.orderId = myOrder_json.getInt("orderId");
            this.matchingId = myOrder_json.getInt("matchingId");
            this.customerId = myOrder_json.getString("customerId");
            this.restaurantId = myOrder_json.getString("restaurantId");

            String orderContent_str = myOrder_json.getString("orderContent");
            JSONArray orderContent_json = new JSONArray(orderContent_str);
            for(int i = 0; i < orderContent_json.length(); i++){
                this.orderContent.add(new Bucket(orderContent_json.getJSONObject(i)));
            }

            this.orderCost = myOrder_json.getInt("orderCost");
            this.orderSurplus = myOrder_json.getInt("orderSurplus");
            this.customerAddress = myOrder_json.getString("customerAddress");
            this.orderType = myOrder_json.getInt("orderType");
            this.timestamp = myOrder_json.getString("timestamp");
            this.customerLat = myOrder_json.getDouble("customerLat");
            this.customerLng = myOrder_json.getDouble("customerLng");

        } catch (JSONException e){
            System.out.println("JSON parse error. / My order");
        }
    }
}
