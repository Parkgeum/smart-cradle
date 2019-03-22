package com.electric5.project2019;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LongSummaryStatistics;
import java.util.concurrent.ExecutionException;

/*
TODO: 추후 할일 리스트
nicknameinput -- 게시판 기능
*/

public class JoinActivity extends Activity {
    final static String ip = "http://192.168.0.28:80/users/join"; // TODO: 테스트 시 수정 http://서버주소:80/users/join
    public static LongSummaryStatistics url;

    EditText join_id, join_pw, join_pw2, babyname, birthy, birthm, birthd;
    ImageView setImage; // 비밀번호 일치 확인 이미지
    RadioGroup gendergroup;
    String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Button join2 = (Button) findViewById(R.id.joinbutton2);

        join_id = (EditText) findViewById(R.id.joinidinput);
        join_pw = (EditText) findViewById(R.id.joinpwinput);
        join_pw2 = (EditText) findViewById(R.id.joinpwinput2);
        setImage = (ImageView)findViewById(R.id.setImage);
        babyname = (EditText) findViewById(R.id.babynameinput);
        gendergroup = (RadioGroup) findViewById(R.id.genderGroup);
        birthy = (EditText) findViewById(R.id.birthyear);
        birthm = (EditText) findViewById(R.id.birthmonth);
        birthd = (EditText) findViewById(R.id.birthday);


        // 비밀번호 & 비밀번호 재입력 텍스트 일치하는 지 비교
        join_pw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(join_pw.getText().toString().equals(join_pw2.getText().toString())) {
                    setImage.setImageResource(R.drawable.checked);
                } else {
                    setImage.setImageResource(R.drawable.unchecked);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        // RadioButton의 체크 상태에 따라 변화값을 주기 위해 setOncheckedChangeLinstener()메소드를 사용
        gendergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.genderFemale:
                        gender = "F";
                        break;
                    case R.id.genderMale:
                        gender = "M";
                        break;
                }
            }
        });

        // Join 버튼이 눌리면 LoginActivity로 가게함
        join2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 필수 입력 -- 입력하지 않았을 시 회원가입 불가
                if (join_id.getText().toString().length()==0) {
                    Toast.makeText(JoinActivity.this, "ID를 입력하세요", Toast.LENGTH_SHORT).show();
                    join_id.requestFocus();
                    return;
                }
                if (join_pw.getText().toString().length()==0) {
                    Toast.makeText(JoinActivity.this, "password를 입력하세요", Toast.LENGTH_SHORT).show();
                    join_pw.requestFocus();
                    return;
                }
                if (join_pw2.getText().toString().length()==0) {
                    Toast.makeText(JoinActivity.this, "password 재확인을 입력하세요", Toast.LENGTH_SHORT).show();
                    join_pw2.requestFocus();
                    return;
                }
                if (babyname.getText().toString().length()==0) {
                    Toast.makeText(JoinActivity.this, "아기 이름을 입력하세요", Toast.LENGTH_SHORT).show();
                    babyname.requestFocus();
                    return;
                }
                if (birthy.getText().toString().length()==0) {
                    Toast.makeText(JoinActivity.this, "생년을 입력하세요", Toast.LENGTH_SHORT).show();
                    birthy.requestFocus();
                    return;
                }
                if (birthm.getText().toString().length()==0) {
                    Toast.makeText(JoinActivity.this, "생월을 입력하세요", Toast.LENGTH_SHORT).show();
                    birthm.requestFocus();
                    return;
                }
                if (birthd.getText().toString().length()==0) {
                    Toast.makeText(JoinActivity.this, "생일을 입력하세요", Toast.LENGTH_SHORT).show();
                    birthd.requestFocus();
                    return;
                }

                // 비밀번호&비밀번호확인 -- 일치하지 않으면 회원가입 불가
                if(join_pw.getText().toString().equals(join_pw2.getText().toString())) {  }
                else {
                    Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    join_pw2.requestFocus();
                    return;
                }

                // JSON생성 : JSONObject는 JSON형태의 데이터를 관리해 주는 메서드
                JSONObject postDataParam = new JSONObject();

                try {
                    //데이터 집어넣기
                    postDataParam.put("id", join_id.getText().toString());
                    postDataParam.put("password", join_pw.getText().toString());
                    postDataParam.put("baby", babyname.getText().toString());
                    postDataParam.put("gender", gender);
                    postDataParam.put("Byear", birthy.getText().toString());
                    postDataParam.put("Bmonth", birthm.getText().toString());
                    postDataParam.put("Bday", birthd.getText().toString());

                    String result = new JoinRequest(JoinActivity.this).execute(postDataParam).get();
                    JSONObject jsonObject = new JSONObject(result);
                    String success = jsonObject.getString("success");

                    // id 중복 체크
                    if (success.equals("true")) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"회원가입 완료",Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"아이디가 중복됩니다",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("TAG", "JSONEXception");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
