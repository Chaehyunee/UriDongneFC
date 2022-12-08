package com.example.uridongnefc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    /** firebase Auth 연결 **/
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private EditText id;
    private EditText pw;
    private TextView register;
    private Button login;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        /** 로그인 firebase Auth 연결 **/
        firebaseAuth = FirebaseAuth.getInstance();

        id = (EditText) findViewById(R.id.Login_id);
        pw = (EditText) findViewById(R.id.Login_password);
        register = (TextView) findViewById(R.id.register_text);
        login = (Button) findViewById(R.id.login_btn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegionChooseActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = id.getText().toString().trim();
                String user_pw = pw.getText().toString().trim();

                if (!user_id.equals("")&&!user_pw.equals("")) {

                    /** 로그인 진행 **/
                    loginUser(user_id, user_pw);

                }
                else
                {
                    Toast.makeText(LoginActivity.this, "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
                }

                firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("email", user_id);
                            startActivity(intent);
                            finish();
                        } else {
                        }
                    }
                };

            }

            private void loginUser(String user_id, String user_pw) {

                firebaseAuth.signInWithEmailAndPassword(user_id, user_pw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            firebaseAuth.addAuthStateListener(firebaseAuthListener);
                        }else {
                            // 로그인 실패
                            Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });


    }
}
