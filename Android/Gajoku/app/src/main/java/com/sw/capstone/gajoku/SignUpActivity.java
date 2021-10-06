package com.sw.capstone.gajoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sw.capstone.gajoku.http.RequestHttp;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText id, password, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        id = findViewById(R.id.editText_signup_id);
        password = findViewById(R.id.editText_signup_pw);
        phone = findViewById(R.id.editText_signup_phone);

        TextView signUpBtn = findViewById(R.id.textView_signup_signup);
        signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.textView_signup_signup:
                String user_id = id.getText().toString();
                String user_pw = password.getText().toString();
                String user_phone = phone.getText().toString();

                Url_SignUp url_signUp = new Url_SignUp(user_id, user_pw, user_phone);
                String dataParameter = url_signUp.getPostDataParameter();

                RequestHttp requestHttp = new RequestHttp();
                String result = requestHttp.requestPost(url_signUp.url, dataParameter);

                if(result.equals("success")){
                    Toast.makeText(this, "회원가입 성공! 다시 로그인해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                } else if(result.equals("id is duplicated")){
                    Toast.makeText(this, "중복된 아이디가 존재합니다", Toast.LENGTH_LONG).show();
                } else if (result.equals("phone_number is duplicated")){
                    Toast.makeText(this, "중복된 핸드폰 번호가 존재합니다", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "회원 가입에 실패했습니다.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
