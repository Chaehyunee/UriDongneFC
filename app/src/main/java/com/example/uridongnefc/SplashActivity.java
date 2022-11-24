package com.example.uridongnefc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

public class SplashActivity extends Activity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication application = (MyApplication) getApplication();

        Boolean firstCheckFlag;
        firstCheckFlag = PreferenceManager.getBoolean(SplashActivity.this, "first_check_flag");

        String Auto_Login = PreferenceManager.getString(SplashActivity.this,"user_id");

        Log.d("test check flag : ", firstCheckFlag.toString());
        Log.d("test check flag : ", Auto_Login.toString());


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent;

                if(!Auto_Login.isEmpty())
                {
                    intent = intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(firstCheckFlag == true)
                {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    intent = new Intent(SplashActivity.this, HelloChooseActivity.class);
                    startActivity(intent);
                }



                finish();
            }
        },3000);

    }
}
