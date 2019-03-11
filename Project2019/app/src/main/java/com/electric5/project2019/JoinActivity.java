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
TODO: idcheckbutton 클릭이벤트 : joinidinput 중복체크(DB에 id 값이 있는지) - 토스트메시지 띄우기
TODO: joinpwinput, joinpwinput2 값 일치해야 함
TODO: nicknameinput
TODO: Join 버튼이 눌리면 LoginActivity로 되돌아 가게
*/

public class JoinActivity extends Activity {

    //TODO: url값 금정이한테 받아서 수정 ---  일단 로컬호스트로 테스트
    final static String ip = "http://서버주소:80/users/join"; //로컬호스트 10.0.2.2
    public static LongSummaryStatistics url;

    EditText join_pw, join_pw2;
    ImageView setImage;
    RadioGroup gendergroup;
    // RadioButton genderF, genderM;
    String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Button join2 = (Button) findViewById(R.id.joinbutton2);
        Button idcheck = (Button) findViewById(R.id.idcheckbutton); // id 중복 체크 버튼

        setImage = (ImageView)findViewById(R.id.setImage); // 비밀번호 일치 확인 이미지


/*      // TODO: idcheck 기능 구현
        idcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //id가 db에 있을 경우
                Toast.makeText(getApplicationContext(), "이미 사용 중인 ID 입니다", Toast.LENGTH_LONG).show();
                //id가 db에 없을 경우
                Toast.makeText(getApplicationContext(), "사용 가능한 ID 입니다", Toast.LENGTH_LONG).show();
            }
        });
*/

        //Join 버튼이 눌리면 LoginActivity로 가게함
        // TODO: 창 쌓이지 않게 코드 수정
        join2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText join_id = (EditText) findViewById(R.id.joinidinput);
                join_pw = (EditText) findViewById(R.id.joinpwinput);
                join_pw2 = (EditText) findViewById(R.id.joinpwinput2);

                EditText babyname = (EditText) findViewById(R.id.babynameinput);

                gendergroup = (RadioGroup) findViewById(R.id.genderGroup);

                EditText birthy = (EditText) findViewById(R.id.birthyear);
                EditText birthm = (EditText) findViewById(R.id.birthmonth);
                EditText birthd = (EditText) findViewById(R.id.birthday);

/*
                 비밀번호 & 비밀번호 재입력 텍스트 일치하는 지 비교
                join_pw2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if(join_pw.getText().toString().equals(join_pw2.getText().toString())) {
                            setImage.setImageResource(R.drawable.unchecked);
                        } else {
                            setImage.setImageResource(R.drawable.checked);
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
*/

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



                //JSON생성 : JSONObject는 JSON형태의 데이터를 관리해 주는 메서드
                JSONObject postDataParam = new JSONObject();

                try {
                    postDataParam.put("id", join_id.getText().toString());//데이터 집어넣기
                    postDataParam.put("password", join_pw.getText().toString());
                    //postDataParam.put("password2", join_pw2.getText().toString());

                    postDataParam.put("baby", babyname.getText().toString());
                    postDataParam.put("gender", gender);

                    postDataParam.put("Byear", birthy.getText().toString());
                    postDataParam.put("Bmonth", birthm.getText().toString());
                    postDataParam.put("Bday", birthd.getText().toString());

                    String result = new JoinRequest(JoinActivity.this).execute(postDataParam).get();
                    JSONObject jsonObject = new JSONObject(result);
                    String success = jsonObject.getString("success");

                    if (success.equals("true")) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(),"회원가입 완료",Toast.LENGTH_LONG).show();
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

                ModeChange.act = 1; // 로그인 액티비티로
                new InsertData(JoinActivity.this).execute(postDataParam);
            }
        });
    }

}
