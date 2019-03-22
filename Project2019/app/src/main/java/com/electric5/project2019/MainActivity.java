package com.electric5.project2019;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/*
주요 기능
    button1. 실시간 영상 스트리밍
    button2. 정지 영상 (캡쳐) 모아보기
    button3. 동작 제어
    button4. 게시판 (추후 추가)
    5. 환경 설정
    5-1. 긴급 알림 (푸시) 푸시/소리 onoff
    5-2. 녹음 기능 (엄마 목소리 녹음)
*/

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button)findViewById(R.id.button1); //실시간 영상 스트리밍
        Button b2 = (Button)findViewById(R.id.button2); //정지 영상 (캡쳐) 모아보기
        Button b3 = (Button)findViewById(R.id.button3); //동작 제어
        Button b4 = (Button)findViewById(R.id.button4); //게시판

        ImageButton b5 = (ImageButton)findViewById(R.id.settingbutton); //환경설정

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.button1 : //실시간 영상 스트리밍
                        Intent intent1 = new Intent(MainActivity.this, VideoActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.button2 : //정지 영상 (캡쳐) 모아보기
                        Intent intent2 = new Intent(MainActivity.this, CaptureActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.button3 : //동작 제어
                        Intent intent3 = new Intent(MainActivity.this, ControlActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.button4 : //게시판
                        Intent intent4 = new Intent(MainActivity.this, BoardActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.settingbutton : //환경설정
                        Intent intent5 = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent5);
                        break;
                };
            }
        };

        b1.setOnClickListener(listener);
        b2.setOnClickListener(listener);
        b3.setOnClickListener(listener);
        b4.setOnClickListener(listener);
        b5.setOnClickListener(listener);
    }
}
