package com.electric5.project2019;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
    }

    //날짜 속성
    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    //날짜, 시간 속성
    private String currentDateTimeFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }
}
