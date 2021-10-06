package com.sw.capstone.gajoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sw.capstone.gajoku.http.RequestHttp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchAddressActivity extends AppCompatActivity implements
        View.OnClickListener,
        SearchAddressRecyclerAdapter.OnAddressSelectedListener,
        StoredAddressRecyclerAdapter.OnStoredAddressSelectedListener{

    StoredAddress storedAddress = new StoredAddress();
    ArrayList<Address> addressList = new ArrayList<>();
    ArrayList<String> storedAddressList = new ArrayList<>();
    ImageView searchMark;

    protected RecyclerView recyclerView;
    protected SearchAddressRecyclerAdapter searchAddressRecyclerAdapter;
    protected StoredAddressRecyclerAdapter storedAddressRecyclerAdapter;
    protected RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);
        searchMark = (ImageView) findViewById(R.id.imageView_searchMark);
        searchMark.setOnClickListener(this);

        storedAddressList = storedAddress.getStoredAddress(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_searchAddress);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        storedAddressRecyclerAdapter = new StoredAddressRecyclerAdapter(storedAddressList, getApplicationContext());
        storedAddressRecyclerAdapter.setOnStoredAddressSelectedListener(SearchAddressActivity.this);
        recyclerView.setAdapter(storedAddressRecyclerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_searchMark:
                addressList.clear();
                EditText jusoEditText = (EditText) findViewById(R.id.editText_juso);
                String juso = jusoEditText.getText().toString();

                Url_SearchAddress url_searchAddress = new Url_SearchAddress(1, juso);
                url_searchAddress.SetCountPerPage(100);

                String url = url_searchAddress.getApiUrl();
                RequestHttp requestHttp = new RequestHttp();
                String result = requestHttp.requestGet(url);
                addressList = url_searchAddress.jsonParse(result);

                if(addressList.size() == 0){
                    System.out.println("No address found.");
                    break;
                }

                searchAddressRecyclerAdapter = new SearchAddressRecyclerAdapter(addressList, getApplicationContext());
                searchAddressRecyclerAdapter.setOnAddressSelectedListener(SearchAddressActivity.this);
                recyclerView.setAdapter(searchAddressRecyclerAdapter);
                break;
            default:
                break;
        }
    }

    @Override
    public void onAddressSelected(int position){
        Address address = addressList.get(position);

        Url_GetLocation url_getLocation = new Url_GetLocation(address.jibunAddress);
        String url = url_getLocation.getApiUrl();
        RequestHttp requestHttp = new RequestHttp();
        String result = requestHttp.requestGet(url);

        try {
            JSONObject res_result = new JSONObject(result);
            double lat = Double.parseDouble(res_result.getString("lat"));
            double lon = Double.parseDouble(res_result.getString("lng"));

            System.out.println("Lat = " + lat + ", Lon = " + lon);

            Intent intent = new Intent(this, InsertDetailAddressActivity.class);
            intent.putExtra("roadAddress", address.roadAddress);
            intent.putExtra("jibunAddress", address.jibunAddress);
            intent.putExtra("lat", lat);
            intent.putExtra("lon", lon);
            startActivity(intent);
        } catch (JSONException e){
            System.out.println("JSON parse error. / coordinateInfo");
        }
    }

    @Override
    public void onStoredAddressSelected(int position){
        String selectedAddress = storedAddressList.get(position);
        storedAddress.setCurrentAddress(this, selectedAddress);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onStoredAddressDeleted(int position){
        String selectedAddress = storedAddressList.get(position);
        storedAddress.removeAddress(this ,selectedAddress);
        if(selectedAddress.equals(storedAddress.getCurrentAddress(this))){
            storedAddress.removeCurrentAddress(this);
        }
        storedAddressList.remove(position);

        storedAddressRecyclerAdapter = new StoredAddressRecyclerAdapter(storedAddressList, getApplicationContext());
        storedAddressRecyclerAdapter.setOnStoredAddressSelectedListener(SearchAddressActivity.this);
        recyclerView.setAdapter(storedAddressRecyclerAdapter);
    }
}
