package com.example.uridongnefc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistTeamActivity extends Activity {

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
    private RequestBody requestBody_user_profile = null;

    private Map<String, RequestBody> user_info;
    private UserVO userData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.regist_team_activity);

        Retrofit retrofit = ApiClient.getApiClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Intent intent=getIntent();
        region = intent.getStringExtra("region");

        regist_email = (EditText) findViewById(R.id.regist_email);
        regist_pw = (EditText) findViewById(R.id.regist_password);
        regist_check = (EditText) findViewById(R.id.regist_checkpw);
        regist_team_name = (EditText) findViewById(R.id.regist_team_name);
        regist_phone_number = (EditText) findViewById(R.id.regist_phone_number);
        regist_button = (Button) findViewById(R.id.regist_button);

        /** DB에 user 정보 등록 **/
        user_info = new HashMap<>();

        // 회원 등록
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
                    RequestBody id = RequestBody.create(MediaType.parse("text/plain"),user_email );
                    RequestBody pw = RequestBody.create(MediaType.parse("text/plain"),user_pw );
                    RequestBody name = RequestBody.create(MediaType.parse("text/plain"),team_name );
                    RequestBody phone_num = RequestBody.create(MediaType.parse("text/plain"),user_phone_number );
                    //RequestBody roll = RequestBody.create(MediaType.parse("int/plain"), roll);

                    user_info.put("id", id);
                    user_info.put("pw", pw);
                    user_info.put("name", name);
                    user_info.put("phone_number", phone_num);
                    //user_info.put("roll", roll);

                    userData = new UserVO();
                    userData.setId(user_email);
                    userData.setPw(user_pw);
                    userData.setName(team_name);
                    userData.setPhone_number(user_phone_number);



                    //profile
                    if(requestBody_user_profile!=null)
                    {
                        MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("profile","profile",requestBody_user_profile);
                        RequestBody profile_flag = RequestBody.create(MediaType.parse("text/plain"),"true" );
                        user_info.put("profile_flag", profile_flag);

                        Call<UserVO> call = apiInterface.registUser(user_info, uploadFile);

                        call.enqueue(new Callback<UserVO>() {
                            @Override
                            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                                UserVO checkuser = response.body();
                                Log.d("test성공",checkuser.getId());
                                if(checkuser.getId().equals("null"))
                                {
                                    Toast.makeText(getApplicationContext(),"등록된 Email 입니다.", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    userData.setProfile_flag(true);
                                    Regist_Team_Success(userData);
                                    Intent intent = new Intent(RegistTeamActivity.this, MainActivity.class);
                                    startActivity(intent);

                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserVO> call, Throwable t) {
                                Log.d("test실패",t.getMessage());
                                Toast.makeText(getApplicationContext(),"가입 실패.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {
                        Call<UserVO> call = apiInterface.registUser_nonProfile(user_info);

                        call.enqueue(new Callback<UserVO>() {
                            @Override
                            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                                UserVO checkuser = response.body();
                                Log.d("test성공",checkuser.getId());
                                if(checkuser.getId().equals("null"))
                                {
                                    Toast.makeText(getApplicationContext(),"등록된 Email 입니다.", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    userData.setProfile_flag(false);
                                    Regist_Team_Success(userData);
                                    Intent intent = new Intent(RegistTeamActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<UserVO> call, Throwable t) {
                                Log.d("test실패",t.getMessage());
                                Toast.makeText(getApplicationContext(),"가입 실패.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }



                }


            }
        });



    }


    private void Regist_Team_Success(UserVO uservo)
    {
        Toast.makeText(getApplicationContext(),"가입 성공.", Toast.LENGTH_SHORT).show();
        PreferenceManager.setBoolean(RegistTeamActivity.this, "first_check_flag",true);
        PreferenceManager.setString(RegistTeamActivity.this,"user_id", user_email);
        PreferenceManager.setString(RegistTeamActivity.this,"user_pw",user_pw);
        PreferenceManager.setString(RegistTeamActivity.this,"team_name", team_name);
        PreferenceManager.setString(RegistTeamActivity.this,"user_phone_number", user_phone_number);

        MyApplication.user_data = uservo;

    }


}
