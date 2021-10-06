package com.example.daeha.badalapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daeha.badalapplication.http.RequestHttp;

public class MainActivity extends NavigationActivity implements View.OnClickListener{
    TextView state;

    Button waiting;
    Button delivering;
    Button leaveWork;
    String id;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        state = findViewById(R.id.textView_state);

        waiting = findViewById(R.id.button_waiting);
        delivering = findViewById(R.id.button_delivering);
        leaveWork = findViewById(R.id.button_leaveWork);

        waiting.setOnClickListener(this);
        delivering.setOnClickListener(this);
        leaveWork.setOnClickListener(this);

        Intent intent = getIntent();

        id = intent.getExtras().getString("ID");

        state.setText(id + " 님");

        Toast.makeText(getApplicationContext(), id+" 님이 돈 벌러 왔습니다.", Toast.LENGTH_SHORT).show();
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
        switch(v.getId()) {
            case R.id.button_waiting:
                state.setText("콜 대기중...");

                Intent intent1 = new Intent(this, CallService.class);
                intent1.putExtra("Id", id);
                startService(intent1);
                break;

            case R.id.button_delivering:
                state.setText("쉬엄쉬엄 일해요~");
                Intent intent2 = new Intent(this, CallService.class);
                stopService(intent2);

                break;

            case R.id.button_leaveWork:
                Intent i = new Intent(this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
