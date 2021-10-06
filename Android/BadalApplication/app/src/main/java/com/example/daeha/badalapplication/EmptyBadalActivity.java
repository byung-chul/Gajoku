package com.example.daeha.badalapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.daeha.badalapplication.http.RequestHttp;
import com.skt.Tmap.TMapTapi;

import java.util.HashMap;

public class EmptyBadalActivity extends AppCompatActivity implements View.OnClickListener {

    Button back;
    TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_badal);

        back = findViewById(R.id.button_back);
        back.setOnClickListener(this);

        empty = findViewById(R.id.textView_empty);
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
        switch(view.getId()){
            case R.id.button_back:
                Intent intent2 = new Intent(this, CallService.class);
                stopService(intent2);

                finish();
                break;
        }
    }
}
