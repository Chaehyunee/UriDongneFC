package com.example.uridongnefc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try{

            Thread.sleep(2000);


        }catch (InterruptedException e) {

            e.printStackTrace();

        }


        Intent intent=new Intent(this, HelloChooseActivity.class);

        startActivity(intent);

        finish();




    }

}



//첫번째 시도

//
//public class SplashActivity extends AppCompatActivity {
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//
//        setContentView(R.layout.activity_splash);
//
////        moveMain(1);	//1초 후 main activity 로 넘어감
//
//
//        //new Intent(현재 context, 이동할 activity)
//        Intent intent = new Intent(SplashActivity.this, HelloChooseActivity.class);
//
//        startActivity(intent);    //intent 에 명시된 액티비티로 이동
//
//        finish();    //현재 액티비티 종료
//
//
//    }
//}