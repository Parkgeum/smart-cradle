package com.electric5.project2019;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ModeChange extends AppCompatActivity {

    static int act = 2; // 기본값 2로  : 메인 액티비티로

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_change);

        Handler hd = new Handler();
        hd.postDelayed(new modechangehandler(),1000);
    }

    private class modechangehandler implements Runnable{
        @Override
        public void run() {
            startActivity(new Intent(getApplication(),LoginActivity.class));
            ModeChange.this.finish();
        }
    }
}