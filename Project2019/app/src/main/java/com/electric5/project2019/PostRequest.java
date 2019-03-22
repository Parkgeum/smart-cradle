package com.electric5.project2019;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.electric5.project2019.ModeChange.act;

public class PostRequest extends AsyncTask <JSONObject, Void, String> {
    Activity activity;
    URL url;
    static JSONObject user = null;

    public PostRequest(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(JSONObject... postDataParams) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(3000 /* milliseconds */); //원래 10000
            conn.setConnectTimeout(3000 /* milliseconds */); //원래 10000
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            String str = getPostDataString(postDataParams[0]);
            Log.e("params", "Post String = " + str);
            writer.write(str);
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                if(ModeChange.act==2) { // 메인 액티비티로
                    JSONObject s = null;
                    s = new JSONObject(sb.toString());
                    if (s.get("success").toString() == "true") {
                        JSONObject json = null;

                        try {
                            json = new JSONObject(sb.toString());
                            user = new JSONObject(json.get("user").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println(json.get("user"));
                    }
                }
                in.close();
                return sb.toString();

            } else {
                return new String("Server Error : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if(act==1) { // 로그인 액티비티로
            Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity,LoginActivity.class);
            activity.startActivity(intent);
        }
        else if(act==2){ // 메인 액티비티로
            Intent intent1 = new Intent(activity,MainActivity.class);
            activity.startActivity(intent1);
            Toast.makeText(activity, "로그인에 성공하였습니다.", Toast.LENGTH_LONG).show();
        }
        else if(act==3){
            Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
        }
    }
    private String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)       //boolean first=true일 때 false로
                first = false;
            else            //false일 때
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}
