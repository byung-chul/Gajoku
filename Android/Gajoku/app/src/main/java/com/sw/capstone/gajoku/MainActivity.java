package com.sw.capstone.gajoku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sw.capstone.gajoku.http.RequestHttp;

public class MainActivity extends NavigationActivity implements View.OnClickListener{
    Button kakaoLogin;
    ImageView korean, chinese, chicken, pizza, bunsik, bojok, dosirak, fastfood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView navigationIcon = findViewById(R.id.imageView_menuIcon);
        navigationIcon.setOnClickListener(this);

        // initiate foodType's imageView
        korean = findViewById(R.id.button_foodType_korean);
        chinese = findViewById(R.id.button_foodType_chinese);
        chicken = findViewById(R.id.button_foodType_chicken);
        pizza = findViewById(R.id.button_foodType_pizza);
        bunsik = findViewById(R.id.button_foodType_bunsik);
        bojok = findViewById(R.id.button_foodType_bojok);
        dosirak = findViewById(R.id.button_foodType_dosirak);
        fastfood = findViewById(R.id.button_foodType_fastfood);

        // set foodType's imageView onClickListener
        korean.setOnClickListener(this);
        chinese.setOnClickListener(this);
        chicken.setOnClickListener(this);
        pizza.setOnClickListener(this);
        bunsik.setOnClickListener(this);
        bojok.setOnClickListener(this);
        dosirak.setOnClickListener(this);
        fastfood.setOnClickListener(this);

        StoredAddress storedAddress = new StoredAddress();
        String currentAddress = storedAddress.getCurrentAddress(this);
        TextView myAddress = findViewById(R.id.textView_myAddress);
        myAddress.setText(currentAddress);
    }

    @Override
    public void onResume(){
        super.onResume();
        StoredAddress storedAddress = new StoredAddress();
        String currentAddress = storedAddress.getCurrentAddress(this);
        TextView myAddress = findViewById(R.id.textView_myAddress);
        myAddress.setText(currentAddress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_foodType_korean:
                startRestaurentActivity("한식");
                break;
            case R.id.button_foodType_chinese:
                startRestaurentActivity("중국집");
                break;
            case R.id.button_foodType_chicken:
                startRestaurentActivity("치킨");
                break;
            case R.id.button_foodType_pizza:
                startRestaurentActivity("피자");
                break;
            case R.id.button_foodType_bunsik:
                startRestaurentActivity("분식");
                break;
            case R.id.button_foodType_bojok:
                startRestaurentActivity("보쌈족발");
                break;
            case R.id.button_foodType_dosirak:
                startRestaurentActivity("도시락");
                break;
            case R.id.button_foodType_fastfood:
                startRestaurentActivity("패스트푸드점");
                break;
            case R.id.imageView_menuIcon:
                super.drawerLayout.openDrawer(Gravity.START);
                break;
            default:
                break;
        }
    }

    public void startRestaurentActivity(String menuType) {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra("MenuType", menuType);
        startActivity(intent);
    }
}
