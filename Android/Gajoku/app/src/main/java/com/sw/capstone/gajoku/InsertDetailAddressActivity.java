package com.sw.capstone.gajoku;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class InsertDetailAddressActivity extends AppCompatActivity implements View.OnClickListener{

    TMapView tMapView;
    String jibunAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_detail_address);
        String roadAddress = getIntent().getStringExtra("roadAddress");
        jibunAddress = getIntent().getStringExtra("jibunAddress");
        double lat = getIntent().getDoubleExtra("lat", 0.000000);
        double lon = getIntent().getDoubleExtra("lon", 0.000000);

        Button acceptButton = findViewById(R.id.button_detailAddress);
        acceptButton.setOnClickListener(this);

        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey( "c5a1cb6b-8b70-4984-900f-7a1908178078" );
        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
            @Override
            public boolean onPressEvent(ArrayList arrayList, ArrayList arrayList1, TMapPoint tMapPoint, PointF pointF) {
                addMarkerItem(tMapPoint);
                return false;
            }

            @Override
            public boolean onPressUpEvent(ArrayList arrayList, ArrayList arrayList1, TMapPoint tMapPoint, PointF pointF) {
                return false;
            }
        });

        linearLayoutTmap.addView( tMapView );

        addMarkerItem(new TMapPoint(lat, lon));
        tMapView.setCenterPoint(lon, lat);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_detailAddress:
                EditText editText_detail = findViewById(R.id.editText_detailAddress);
                String detailAddress = editText_detail.getText().toString();
                jibunAddress += " " + detailAddress;

                StoredAddress storedAddress = new StoredAddress();
                storedAddress.storeAddress(this, jibunAddress);
                storedAddress.setCurrentAddress(this, jibunAddress);

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void addMarkerItem(TMapPoint tMapPoint){
        tMapView.removeAllMarkerItem();

        TMapMarkerItem marker = new TMapMarkerItem();
        Bitmap bitmap = BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.clustering);

        marker.setIcon(bitmap); // 마커 아이콘 지정
        marker.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        marker.setTMapPoint( tMapPoint ); // 마커의 좌표 지정
        marker.setName("여기가 나의 위치!"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem", marker); // 지도에 마커
    }
}
