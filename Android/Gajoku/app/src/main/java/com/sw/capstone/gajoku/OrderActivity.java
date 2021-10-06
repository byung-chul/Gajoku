package com.sw.capstone.gajoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener{

    int time = 0;
    int surplus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        String restaurantName = getIntent().getStringExtra("restaurantName");
        int restaurantLeast = getIntent().getIntExtra("restaurantLeast", 0);
        int total = getIntent().getIntExtra("Total", 0);

        TextView nameTextView = findViewById(R.id.textView_order_restaurantTitle);
        nameTextView.setText(restaurantName);
        TextView leastTextView = findViewById(R.id.textView_order_leastCost);
        leastTextView.setText(restaurantLeast + " 원");
        TextView totalTextView = findViewById(R.id.textView_order_totalCost);
        totalTextView.setText(total + " 원");
        TextView surplusTextView = findViewById(R.id.textView_order_surplus);

        final TextView timeTextView = findViewById(R.id.textView_order_time);
        timeTextView.setText("0시간 0분");

        final TextView orderButton = findViewById(R.id.textView_order_order);
        orderButton.setOnClickListener(this);

        SeekBar timeLine = findViewById(R.id.seekBar_order_time);
        timeLine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeTextView.setText(progress / 60 + "시간 " + progress % 60 + "분");
                time = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        surplus = restaurantLeast - total;
        if(surplus > 0){
            surplusTextView.setText("최소 주문금액을 만족하지 못합니다.\n대기를 원하는 시간을 설정해주세요.");
        } else {
            surplusTextView.setText("최소 주문금액을 만족합니다!");
            timeLine.setVisibility(View.INVISIBLE);
            timeTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.textView_order_order:
                if(time >= 10 || surplus <= 0) {
                    Intent intent = new Intent();
                    intent.putExtra("Time", time);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(this, "최소 10분은 기다려주세요!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
