package com.electric5.project2019;

import android.app.Activity;

import java.net.MalformedURLException;
import java.net.URL;

//회원가입
public class JoinRequest extends PostRequest {
    //final static String Url = "http://192.168.200.125:3000";

    public JoinRequest(Activity activity) {
        super(activity);
    }

    @Override
    protected void onPreExecute() {
        try {
            String ip = "http://서버주소:80";
            url = new URL(ip + "/users/join");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
