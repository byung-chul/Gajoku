package com.sw.capstone.gajoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sw.capstone.gajoku.http.RequestHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyOrderActivity extends AppCompatActivity {

    ArrayList<MyOrder> myOrderList = new ArrayList<>();
    protected RecyclerView orderListRecyclerView;
    protected MyOrderRecyclerAdapter myOrderRecyclerAdapter;
    protected RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        // get user id from android database
        StoredUserSession storedUserSession = new StoredUserSession(this);
        String u_id = storedUserSession.getUserSession();

        // HTTP Request
        Url_GetMyOrder url_getMyOrder = new Url_GetMyOrder(u_id);
        String apiUrl = url_getMyOrder.getApiUrl();

        RequestHttp requestHttp = new RequestHttp();
        String response = requestHttp.requestGet(apiUrl);

        // parse JSON
        try {
            JSONArray myOrderResponse = new JSONArray(response);
            for(int i = 0; i < myOrderResponse.length(); i++) {
                myOrderList.add(new MyOrder(myOrderResponse.getJSONObject(i)));
            }
        } catch (JSONException e){
            System.out.println("JSON parse error. / My order List");
        }

        // set MyOrder RecyclerView
        orderListRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_myorder);
        layoutManager = new LinearLayoutManager(this);
        orderListRecyclerView.setLayoutManager(layoutManager);

        myOrderRecyclerAdapter = new MyOrderRecyclerAdapter(myOrderList);
        orderListRecyclerView.setAdapter(myOrderRecyclerAdapter);
    }
}
