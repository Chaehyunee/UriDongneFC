package com.example.uridongnefc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ContactPopupActivity extends Activity {

    /** xml 요소 **/
    private TextView phone_number_text;
    private Button cancel_btn;
    private Button contact_btn;

    /** String 값 **/
    private String phone_number;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** 타이틀바 없애기 **/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.contact_popup_activity);



        /** xml 요소 지정 **/
        phone_number_text = (TextView) findViewById(R.id.phone_number_text);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        contact_btn = (Button) findViewById(R.id.contact_btn);



        /** phone_number 받아오기 **/
        Intent intent = getIntent();
        phone_number = intent.getStringExtra("phone_number");
        Log.d("test result phone_number : ", phone_number);
        phone_number_text.setText("전화번호 : " + phone_number);



        /** 취소 버튼 **/
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        /** 전화 열기 **/
        contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call_open_intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone_number));
                startActivity(call_open_intent);
                finish();
            }
        });


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_OUTSIDE)
        {
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
