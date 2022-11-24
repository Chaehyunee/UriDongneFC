package com.example.uridongnefc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends Activity {

    private EditText id;
    private EditText pw;
    private TextView register;
    private Button login;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Retrofit retrofit = ApiClient.getApiClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

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

                UserVO user = new UserVO();
                user.setId(user_id);
                user.setPw(user_pw);

                Call<UserVO> call = apiInterface.login(user);
                call.enqueue(new Callback<UserVO>() {
                    @Override
                    public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                        UserVO get_user_info = response.body();

                        if(!get_user_info.getId().equals("null"))
                        {
                            MyApplication.user_data = get_user_info;
                            PreferenceManager.setString(LoginActivity.this,"user_id", get_user_info.getId());
                            PreferenceManager.setString(LoginActivity.this,"user_pw", get_user_info.getPw());
                            PreferenceManager.setString(LoginActivity.this,"user_name", get_user_info.getName());
                            Log.d("로그인 성공",get_user_info.getId().toString());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                            Log.d("로그인 실패","fail");

                        }

                    }

                    @Override
                    public void onFailure(Call<UserVO> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}
