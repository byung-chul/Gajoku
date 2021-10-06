package com.sw.capstone.gajoku;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class StoredOrder {
    Context context;

    StoredOrder(Context context){
        this.context = context;
    }

    public ArrayList<Order> getStoredOrderList(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Order", MODE_PRIVATE);
        String orderList_json = sharedPreferences.getString("List", null);

        ArrayList<Order> orderList;
        Gson gson = new Gson();

        if(orderList_json != null){
            Type type = new TypeToken<ArrayList<Order>>(){}.getType();
            orderList = gson.fromJson(orderList_json, type);
        } else {
            orderList = new ArrayList<>();
        }

        return orderList;
    }

    public void storeOrder(Order order){
        ArrayList<Order> orderList = getStoredOrderList();

        orderList.add(order);
        storeOrderList(orderList);
    }

    public void resetOrder(ArrayList<Order> orderList){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Order", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        storeOrderList(orderList);
    }

    private void storeOrderList(ArrayList<Order> orderList){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Order", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String orderList_json = gson.toJson(orderList);

        editor.putString("List", orderList_json);
        editor.apply();
    }
}
