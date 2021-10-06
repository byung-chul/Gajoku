package com.example.daeha.badalapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogoutActivity extends NavigationActivity implements View.OnClickListener {

    TextView userId;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        userId = findViewById(R.id.textView_userId);
        logout = findViewById(R.id.button_logout);
        logout.setOnClickListener(this);


        Intent intent = getIntent();

        String id = intent.getExtras().getString("ID");
    }

    @Override
    public void onClick(View view) {

        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);

    }
}
