package com.example.uridongnefc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ReadingTeamActivity extends AppCompatActivity {

    /** TextView 선언 **/
    private TextView reading_team_name;
    private TextView reading_team_title;
    private TextView reading_team_day;
    private TextView reading_team_time;
    private TextView reading_team_field_name;
    private TextView reading_team_money;
    private TextView reading_team_info;


    /** ImageView 선언 **/
    private ImageView back_btn;
    private ImageView team_contact_btn;

    /** 내용 담을 String 선언 **/
    private String name;
    private String title;
    private String days;
    private String time;
    private String field;
    private String money;
    private String story;

    /** Firebase Store result **/
    private Map<String, Object> result;

    /** documentID, region 선언 **/
    private String documentID;
    private String region;

    /** Thread 사용 **/
    Handler mHandler = null;

    /** Firebase Store Setting **/
    private FirebaseFirestore db;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reading_team_activity);

        /** Thread 관련 변수 **/
        mHandler = new Handler();

        /** documentID, region 받아오기 **/
        Intent intent = getIntent();
        documentID = intent.getStringExtra("documentID");
        region = intent.getStringExtra("region");
        Log.d("test result documentID, region : ", documentID + region);


        /** Firebase Store 초기화 **/
        db = FirebaseFirestore.getInstance();

        /** TextView 지정 **/
        reading_team_name = (TextView) findViewById(R.id.reading_team_name);
        reading_team_title = (TextView) findViewById(R.id.reading_team_title);
        reading_team_day = (TextView) findViewById(R.id.reading_team_day);
        reading_team_time = (TextView) findViewById(R.id.reading_team_time);
        reading_team_field_name = (TextView) findViewById(R.id.reading_team_field_name);
        reading_team_money = (TextView) findViewById(R.id.reading_team_money);
        reading_team_info = (TextView) findViewById(R.id.reading_team_info);

        /** ImageView 지정 **/
        back_btn = (ImageView) findViewById(R.id.back_btn);
        team_contact_btn = (ImageView) findViewById(R.id.team_contact_btn);

        /** 뒤로가기 버튼 **/
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /** 연락하기 버튼 **/
        team_contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO contact 버튼 연결 - 팀
            }
        });


        /** Firebase Store 에서 게시물 정보 받아오기 **/
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (documentID != null) {
                            DocumentReference docRef = db.collection(region).document(documentID);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            result = document.getData();
                                            name = (String) result.get("name");
                                            title = (String) result.get("title");
                                            days = (String) result.get("days");
                                            time = (String) result.get("time");
                                            field = (String) result.get("field");
                                            money = (String) result.get("money");
                                            story = (String) result.get("story");


                                            /** TextView 값 넣기 **/
                                            reading_team_name.setText(name);
                                            reading_team_title.setText(title);
                                            reading_team_day.setText(days);
                                            reading_team_time.setText(time);
                                            reading_team_field_name.setText(field);
                                            reading_team_money.setText(money);
                                            reading_team_info.setText(story);

                                            Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                        } else {
                                            Log.d("TAG", "No such document");
                                        }
                                    } else {
                                        Log.d("TAG", "get failed with ", task.getException());
                                    }
                                }
                            });
                        }
                        else {
                            Log.w("document ID is", "null");
                        }
                    }
                });
            }
        });

        t.start();



    }
}
