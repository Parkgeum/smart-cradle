package com.electric5.project2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

//TODO: loginbutton 클릭이벤트 : idinput, pwinput 값 받아서 회원 확인 후 Main으로 넘어가기


public class LoginActivity extends AppCompatActivity {

    //TODO: url값 금정이한테 받아서 수정
    final static String url = "http://172.30.79.216:3000/users/login"; //로컬호스트 10.0.2.2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button join = (Button)findViewById(R.id.joinbutton);
        Button login = (Button)findViewById(R.id.loginbutton);

        //Login 버튼이 눌리면 MainActivity로 가게함
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText login_id = (EditText) findViewById(R.id.idinput);
                EditText login_pw = (EditText) findViewById(R.id.pwinput);

                JSONObject postDataParam_login = new JSONObject(); //JSON생성 : JSONObject는 JSON형태의 데이터를 관리해 주는 메서드

                try {
                    postDataParam_login.put("id", login_id.getText().toString()); // 데이터 집어넣기
                    postDataParam_login.put("password", login_pw.getText().toString());
                } catch (JSONException e) {
                    Log.e("TAG", "JSONEXception");
                }
                ModeChange.act=2; // 메인 액티비티로
                new InsertData(LoginActivity.this).execute(postDataParam_login);

            }
        });


        //Join 버튼이 눌리면 JoinActivity로 가게함
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotojoin = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(gotojoin);
            }
        });

    }
}
