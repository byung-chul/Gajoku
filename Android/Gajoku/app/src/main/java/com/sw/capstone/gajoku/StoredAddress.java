package com.sw.capstone.gajoku;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;


public class StoredAddress{

    public ArrayList<String> getStoredAddress(Context context){
        ArrayList<String> addressList = new ArrayList<>();

        SharedPreferences mainAddress = context.getSharedPreferences("Address", MODE_PRIVATE);
        Set<String> AddressList_set = mainAddress.getStringSet("List", new HashSet<>(Arrays.asList("")));
        addressList.addAll(AddressList_set);

        return addressList;
    }

    public void storeAddress(Context context, String address){
        SharedPreferences mainAddress = context.getSharedPreferences("Address", MODE_PRIVATE);
        Set<String> AddressList_set = mainAddress.getStringSet("List", null);

        if(AddressList_set == null) {
            AddressList_set = new HashSet<>(Arrays.asList(new String[]{}));
        }
        AddressList_set.add(address);
        SharedPreferences.Editor editor = mainAddress.edit();
        editor.putStringSet("List", AddressList_set);
        editor.apply();
    }

    public void removeAddress(Context context, String address){
        SharedPreferences mainAddress = context.getSharedPreferences("Address", MODE_PRIVATE);
        Set<String> AddressList_set = mainAddress.getStringSet("List", null);

        if(AddressList_set.contains(address)){
            AddressList_set.remove(address);
        }

        SharedPreferences.Editor editor = mainAddress.edit();
        editor.putStringSet("List", AddressList_set);
        editor.apply();
    }

    public void setCurrentAddress(Context context, String address){
        SharedPreferences currentAddress = context.getSharedPreferences("Address", MODE_PRIVATE);
        SharedPreferences.Editor editor = currentAddress.edit();
        editor.putString("CurrentAddress", address);

        editor.apply();
    }

    public String getCurrentAddress(Context context){
        SharedPreferences currentAddress = context.getSharedPreferences("Address", MODE_PRIVATE);

        return currentAddress.getString("CurrentAddress", "주소를 설정해주세요!");
    }

    public void removeCurrentAddress(Context context){
        SharedPreferences currentAddress = context.getSharedPreferences("Address", MODE_PRIVATE);
        SharedPreferences.Editor editor = currentAddress.edit();
        editor.remove("CurrentAddress");
        editor.apply();
    }
}
