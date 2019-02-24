package com.electric5.project2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
TODO: loginbutton 클릭이벤트 : idinput, pwinput 값 받아서 회원 확인 후 Main으로 넘어가기
*/

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button join = (Button)findViewById(R.id.joinbutton);
        Button login = (Button)findViewById(R.id.loginbutton);

        //Join 버튼이 눌리면 JoinActivity로 가게함
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotojoin = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(gotojoin);
            }
        });

        //Login 버튼이 눌리면 MainActivity로 가게함
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomain = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(gotomain);
            }
        });

    }
}
