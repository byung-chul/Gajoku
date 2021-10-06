package com.example.daeha.badalapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daeha.badalapplication.http.RequestHttp;
import com.skt.Tmap.TMapTapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
public class BadalActivity extends AppCompatActivity implements View.OnClickListener{

    TextView restaurant;
    TextView address;
    TextView detail_rest;
    TextView detail_addr;

    ImageView direction;

    Button receive_call;
    Button reject_call;

    Integer deliveryId;
    String restaurantName;
    String restaurantLon;
    String restaurantLat;

    String firstDestinationName;
    String firstDestinationLon;
    String firstDestinationLat;

    String secondDestinationName;
    String secondDestinationLon;
    String secondDestinationLat;

    Integer numDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badal);

        restaurant = findViewById(R.id.textView_rest);
        address = findViewById(R.id.textView_addr);
        detail_rest = findViewById(R.id.textView_detailRest);
        detail_addr = findViewById(R.id.textView_detailAddr);

        direction = findViewById(R.id.image_direction);

        receive_call = findViewById(R.id.button_receiveCall);
        receive_call.setOnClickListener(this);

        reject_call = findViewById(R.id.button_rejectCall);
        reject_call.setOnClickListener(this);

        Intent i = getIntent();
        String r = i.getExtras().getString("result");

        try {
            JSONArray res_array = new JSONArray(r);
            JSONObject res_object = res_array.getJSONObject(0);

            restaurantName = res_object.getString("r_name");
            restaurantLon = res_object.getString("r_lng");
            restaurantLat = res_object.getString("r_lat");

            firstDestinationName = res_object.getString("c1_address");
            firstDestinationLon = res_object.getString("c1_lng");
            firstDestinationLat = res_object.getString("c1_lat");

            secondDestinationName = res_object.getString("c2_address");
            secondDestinationLon = res_object.getString("c2_lng");
            secondDestinationLat = res_object.getString("c2_lat");

            deliveryId = res_object.getInt("deliveryId");

            detail_rest.setText(restaurantName);

            if(secondDestinationName.equals("null") || firstDestinationName.equals(secondDestinationName)){
                detail_addr.setText(firstDestinationName);
                numDestination = 1;
            }
            else if(firstDestinationLat.equals(secondDestinationLat) && firstDestinationLon.equals(secondDestinationLon)){
                detail_addr.setText(firstDestinationName);
                numDestination = 1;
            }
            else{
                System.out.println("error if");
                detail_addr.setText(secondDestinationName);
                numDestination = 2;
            }

        } catch (JSONException e){
            System.out.println("JSON parse error. / coordinateInfo");
        }

        Intent intent2 = new Intent(this, CallService.class);
        stopService(intent2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);


        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(actionbar);

        Toolbar parent = (Toolbar)actionbar.getParent();
        parent.setContentInsetsAbsolute(0,0);

        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_receiveCall:
                Url_Accept acceptUrl = new Url_Accept(deliveryId);
                RequestHttp requestHttp = new RequestHttp();
                String a_result = requestHttp.requestGet(acceptUrl.getApiUrl());

                System.out.println(a_result);

                Intent intent2 = new Intent(this, CallService.class);
                stopService(intent2);

                TMapTapi tMapTapi = new TMapTapi(this);
                tMapTapi.setSKTMapAuthentication ("c5a1cb6b-8b70-4984-900f-7a1908178078");

                tMapTapi.setOnAuthenticationListener(new TMapTapi.OnAuthenticationListenerCallback() {
                    @Override
                    public void SKTMapApikeySucceed() {
                        System.out.println("api key success");
                    }
                    @Override
                    public void SKTMapApikeyFailed(String errorMsg) {
                        System.out.println("api key failed");
                    }
                });

                System.out.println("Tmap Error1.5");
                HashMap pathInfo = new HashMap();

                if(numDestination == 1){
                    pathInfo.put("rStName", restaurantName);
                    pathInfo.put("rStX", restaurantLon);
                    pathInfo.put("rStY", restaurantLat);

                    pathInfo.put("rGoName", firstDestinationName);
                    pathInfo.put("rGoX", firstDestinationLon);
                    pathInfo.put("rGoY", firstDestinationLat);
                }
                else{
                    pathInfo.put("rGoName", secondDestinationName);
                    pathInfo.put("rGoX", secondDestinationLon);
                    pathInfo.put("rGoY", secondDestinationLat);

                    pathInfo.put("rStName", restaurantName);
                    pathInfo.put("rStX", restaurantLon);
                    pathInfo.put("rStY", restaurantLat);

                    pathInfo.put("rV1Name", firstDestinationName);
                    pathInfo.put("rV1X", firstDestinationLon);
                    pathInfo.put("rV1Y", firstDestinationLat);
                }
                tMapTapi.invokeRoute(pathInfo);

                break;

            case R.id.button_rejectCall:
                Url_Reject rejectUrl = new Url_Reject(deliveryId);
                RequestHttp requestHttp2 = new RequestHttp();
                String r_result = requestHttp2.requestGet(rejectUrl.getApiUrl());

                System.out.println(r_result);
                Intent i = new Intent(this, CallService.class);
                startService(i);

                break;
        }
    }
}
