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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class ReadingPlayerActivity extends AppCompatActivity {

    /** TextView 선언 **/
    private TextView reading_player_name;
    private TextView reading_player_title;
    private TextView reading_player_day;
    private TextView reading_player_time;
    private TextView reading_player_age;
    private TextView reading_player_position;
    private TextView reading_player_info;


    /** ImageView 선언 **/
    private ImageView back_btn;
    private ImageView player_contact_btn;

    /** 내용 담을 String 선언 **/
    private String name="test name";
    private String title;
    private String days;
    private String time;
    private String age;
    private String position;
    private String story;
    private String email;
    private String phone_number;

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
        setContentView(R.layout.reading_player_activity);

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
        reading_player_name = (TextView) findViewById(R.id.reading_player_name);
        reading_player_title = (TextView) findViewById(R.id.reading_player_title);
        reading_player_day = (TextView) findViewById(R.id.reading_player_day);
        reading_player_time = (TextView) findViewById(R.id.reading_player_time);
        reading_player_age = (TextView) findViewById(R.id.reading_player_age);
        reading_player_position = (TextView) findViewById(R.id.reading_player_position);
        reading_player_info = (TextView) findViewById(R.id.reading_player_info);

        /** ImageView 지정 **/
        back_btn = (ImageView) findViewById(R.id.back_btn);
        player_contact_btn = (ImageView) findViewById(R.id.player_contact_btn);

        /** 뒤로가기 버튼 **/
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                                            age = (String) result.get("age");
                                            position = (String) result.get("position");
                                            story = (String) result.get("story");
                                            email = (String) result.get("email");


                                            /** TextView 값 넣기 **/
                                            reading_player_name.setText("[ "+name+" ]");
                                            reading_player_title.setText(title);
                                            reading_player_day.setText(days);
                                            reading_player_time.setText(time);
                                            reading_player_age.setText(age);
                                            reading_player_position.setText(position);
                                            reading_player_info.setText(story);

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



        /** 연락하기 버튼 **/
        player_contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /** Firebase Store 에서 전화번호 정보 받아오기 **/
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                /** Firebase Store collection 선언 **/
                                CollectionReference colRef = db.collection("users_info").document(email).collection(email);

                                if (email != null) {
                                    colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        result = document.getData();
                                                        phone_number = (String) result.get("phone_number");

                                                        Log.d("phone_number 받아오기", phone_number);


                                                        Intent intent = new Intent(ReadingPlayerActivity.this, ContactPopupActivity.class);
                                                        intent.putExtra("phone_number", phone_number);
                                                        startActivity(intent);
                                                    }
                                                }
                                            } else {
                                                Log.d("phone_number 받아오기", "실패");
                                            }
                                        }
                                    });
                                }
                                else {
                                    Log.w("Email is", "null");
                                }


                            }
                        });
                    }
                });

                t.start();


            }
        });

    }
}
