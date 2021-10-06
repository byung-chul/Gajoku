package com.sw.capstone.gajoku;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sw.capstone.gajoku.http.RequestHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuReservationPager extends Fragment{

    View view;

    public MenuReservationPager(){
        super();
    }

    public static Fragment newInstance(int r_id){
        MenuReservationPager f = new MenuReservationPager();
        Bundle bundle = new Bundle();
        bundle.putInt("r_id", r_id);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle bundle = getArguments();
        int r_id = bundle.getInt("r_id", 0);

        view = inflater.inflate(R.layout.pager_reservation, container, false);

        StoredAddress storedAddress = new StoredAddress();
        String u_address = storedAddress.getCurrentAddress(view.getContext());

        Url_GetCurrentOrder url_getCurrentOrder = new Url_GetCurrentOrder(r_id, u_address);
        String apiUrl = url_getCurrentOrder.getApiUrl();

        RequestHttp requestHttp = new RequestHttp();
        String response = requestHttp.requestGet(apiUrl);

        int orderSurplus = 0;
        double distance = 0;

        try {
            JSONArray orderResponse = new JSONArray(response);
            JSONObject orderJson = orderResponse.getJSONObject(0);
            orderSurplus = orderJson.getInt("orderSurplus");
            distance = orderJson.getDouble("distance");
        } catch (JSONException e){
            System.out.println("JSON parse error. / restaurant order info");
        }

        if(orderSurplus != 0){
            view.findViewById(R.id.textView_noReservation).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.imageView_noReservation).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.textView_reservation_surplus).setVisibility(View.VISIBLE);
            view.findViewById(R.id.textView_reservation_surplusText).setVisibility(View.VISIBLE);;
            view.findViewById(R.id.imageView_divider_reservation).setVisibility(View.VISIBLE);

            TextView surplusText = view.findViewById(R.id.textView_reservation_surplus);
            surplusText.setText(orderSurplus + " 원");
        }

        return view;
    }
}