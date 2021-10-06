package com.example.daeha.badalapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daeha.badalapplication.http.RequestHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_login;
    EditText et_id;
    EditText et_pw;
    Button bt_login;

    String login_userId;
    String login_userPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_login = findViewById(R.id.textView_login);

        et_id = findViewById(R.id.editText_id);
        et_pw = findViewById(R.id.editText_pw);

        et_id.getText().clear();
        et_pw.getText().clear();

        bt_login = findViewById(R.id.button_login);
        bt_login.setOnClickListener(this);
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
    public void onClick(View view) {
        login_userId = et_id.getText().toString();
        login_userPw = et_pw.getText().toString();

//        System.out.println(login_userId);
//        System.out.println(login_userPw);

        Url_Login loginUrl = new Url_Login(login_userId, login_userPw);
        RequestHttp requestHttp = new RequestHttp();
        String result = requestHttp.requestGet(loginUrl.getApiUrl());

        System.out.println(result);

        try {
            JSONArray res_array = new JSONArray(result);
            JSONObject res_object = res_array.getJSONObject(0);
            String id = res_object.getString("id");

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("ID", id);
            startActivity(intent);
        } catch (JSONException e){
            System.out.println("JSON parse error. / coordinateInfo");
            Toast.makeText(getApplicationContext(), "잘못된 입력입니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
