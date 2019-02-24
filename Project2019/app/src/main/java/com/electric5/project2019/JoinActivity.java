package com.electric5.project2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
TODO: idcheckbutton 클릭이벤트 : joinidinput 중복체크(DB에 id 값이 있는지) - 토스트메시지 띄우기
TODO: joinpwinput, joinpwinput2 값 일치해야 함
TODO: Join 버튼이 눌리면 LoginActivity로 되돌아 가게 수정
*/

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Button join2 = (Button) findViewById(R.id.joinbutton2);
        Button idcheck = (Button) findViewById(R.id.idcheckbutton);


        idcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //id가 db에 있을 경우
                Toast.makeText(getApplicationContext(), "이미 사용 중인 ID 입니다", Toast.LENGTH_LONG).show();
                //id가 db에 없을 경우
                Toast.makeText(getApplicationContext(), "사용 가능한 ID 입니다", Toast.LENGTH_LONG).show();
            }
        });
    }

}
