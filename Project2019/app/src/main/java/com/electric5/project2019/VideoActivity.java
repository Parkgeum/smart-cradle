package com.electric5.project2019;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.VideoView;


// RTSP 스트리밍 방식 이용
//안되면 이 방식 https://codingcoding.tistory.com/215
public class VideoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        VideoView video = (VideoView) findViewById(R.id.videoview);
        MediaController mc = new MediaController(this);

        //TODO:라즈베리아이피넣어서 테스트
        video.setVideoPath("rtsp://223.194.128.30:8080/test");
        video.start();
    }
}
