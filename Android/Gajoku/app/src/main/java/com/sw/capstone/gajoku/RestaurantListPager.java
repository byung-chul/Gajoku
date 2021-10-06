package com.sw.capstone.gajoku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sw.capstone.gajoku.http.RequestHttp;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RestaurantListPager extends Fragment implements
    RestaurantRecyclerAdapter.OnRestaurantSelectedListener{

    List<Restaurant> restaurants = new ArrayList<>();
    String address;

    RecyclerView recyclerView;
    RestaurantRecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    Integer index;
    View view;

    public RestaurantListPager(){
        super();
    }

    public static Fragment newInstance(Integer index){
        RestaurantListPager f = new RestaurantListPager();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle bundle = getArguments();
        index = bundle.getInt("index");
        view = inflater.inflate(R.layout.pager_restaurant, container, false);
        final TextView text_noRestaurant = view.findViewById(R.id.textView_noRestaurant);
        final ImageView image_noRestaurant = view.findViewById(R.id.imageView_noRestaurant);

        final Handler handler = new Handler();

        new Thread(new Runnable(){
            @Override
            public void run(){
                FoodType foodTypeList = new FoodType();
                String foodType = foodTypeList.getTypeName(index);

                getAddress();
                getRestaurantList(address, foodType);

                handler.post(new Runnable(){
                    @Override
                    public void run(){
                        view.findViewById(R.id.progressBar_restaurant).setVisibility(View.INVISIBLE);
                        if(restaurants.size() != 0) {
                            recyclerView = (RecyclerView) view.findViewById(R.id.restaurant_recyclerView);
                            layoutManager = new LinearLayoutManager(view.getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerAdapter = new RestaurantRecyclerAdapter(restaurants);
                            recyclerAdapter.setOnRestaurantSelectedListener(RestaurantListPager.this);
                            recyclerView.setAdapter(recyclerAdapter);
                        } else {
                            text_noRestaurant.setVisibility(View.VISIBLE);
                            image_noRestaurant.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }).start();

        return view;
    }


    public void getRestaurantList(String address, String menuType){
        Url_GetRestaurant url_getRestaurant = new Url_GetRestaurant(address, menuType);
        String apiUrl = url_getRestaurant.getApiUrl();

        RequestHttp requestHttp = new RequestHttp();
        String response = requestHttp.requestGet(apiUrl);
        restaurants = url_getRestaurant.parseJSON(response);
    }

    public void getAddress(){
        StoredAddress storedAddress = new StoredAddress();
        address = storedAddress.getCurrentAddress(getContext());
        System.out.println("Your address is : " + address);
    }

    @Override
    public void onRestaurantSelected(int position){
        Restaurant restaurant = restaurants.get(position);
        int restaurantId = restaurant.id;
        System.out.println("Restaurant id = " + restaurantId);

        Intent intent = new Intent(view.getContext(), MenuActivity.class);
        intent.putExtra("restaurantId", restaurantId);
        startActivity(intent);
    }
}