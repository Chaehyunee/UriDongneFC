package com.example.uridongnefc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistTeamActivity extends AppCompatActivity {

    private EditText regist_team_name;
    private EditText regist_email;
    private EditText regist_pw;
    private EditText regist_check;
    private EditText regist_phone_number;
    private Button regist_button;


    private String region;
    private String user_email;
    private String user_pw;
    private String check_pw;
    private String team_name;
    private String user_phone_number;
    private int roll;

    /** Firebase Authentication Setting **/
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.regist_team_activity);

        /** Firebase Auth 초기화 **/
        mAuth = FirebaseAuth.getInstance();

        /** 이전 화면에서 region 지역명 받아오기 **/
        Intent intent=getIntent();
        region = intent.getStringExtra("region");

        regist_email = (EditText) findViewById(R.id.regist_email);
        regist_pw = (EditText) findViewById(R.id.regist_password);
        regist_check = (EditText) findViewById(R.id.regist_checkpw);
        regist_team_name = (EditText) findViewById(R.id.regist_team_name);
        regist_phone_number = (EditText) findViewById(R.id.regist_phone_number);
        regist_button = (Button) findViewById(R.id.regist_button);


        /** 회원 등록 **/
        regist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserVO user = new UserVO();

                user_email = regist_email.getText().toString().trim();
                user_pw = regist_pw.getText().toString().trim();
                check_pw = regist_check.getText().toString().trim();
                team_name =regist_team_name.getText().toString().trim();
                user_phone_number =regist_phone_number.getText().toString().trim();
                roll = 0; // team : 0, player : 1

                if(user_email.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(user_pw.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(check_pw.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(!user_pw.equals(check_pw))
                {
                    Toast.makeText(getApplicationContext(),"비밀번호가 동일하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(team_name.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"사용하실 이름을 적어주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(user_phone_number.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"전화번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    createAccount();
                }


            }

            /** 계정 생성 **/
            private void createAccount() {
                mAuth.createUserWithEmailAndPassword(user_email, user_pw)
                        .addOnCompleteListener(RegistTeamActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // 회원가입 성공시
                                    Toast.makeText(RegistTeamActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistTeamActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // 계정이 중복된 경우
                                    Toast.makeText(RegistTeamActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });



    }

}
