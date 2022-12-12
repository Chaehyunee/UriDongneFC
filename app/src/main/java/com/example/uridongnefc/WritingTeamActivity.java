package com.example.uridongnefc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WritingTeamActivity extends AppCompatActivity {

    /** page 이동 버튼 **/
    private ImageView writing_team_back_btn;
    private ImageView writing_team_complete_btn;

    /** team 게시글 작성 내용 **/
    private EditText writing_team_title;
    private EditText writing_team_time;
    private EditText writing_team_field;
    private EditText writing_team_money;
    private EditText writing_team_story;

    private String team_title;
    private String team_time;
    private String team_field;
    private String team_money;
    private String team_story;


    /** team 요일 체크박스 **/
    private CheckBox team_mon;
    private CheckBox team_tue;
    private CheckBox team_wed;
    private CheckBox team_thu;
    private CheckBox team_fri;
    private CheckBox team_sat;
    private CheckBox team_sun;
    private String days = "";


    /** 지역명 받아오기 **/
    private String region;

    /** Team name, email 받아오기 **/
    private String name;
    private String email;


    /** Firebase Authentication Setting **/
    private FirebaseFirestore db;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_team_activity);

        /** 이전 화면에서 region 받아오기 **/
        Intent intent = getIntent();
        region = intent.getStringExtra("region");
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");


        writing_team_back_btn = (ImageView) findViewById(R.id.writing_team_back_btn);
        writing_team_complete_btn = (ImageView) findViewById(R.id.writing_team_complete_btn);

        writing_team_title = (EditText) findViewById(R.id.writing_team_title);
        writing_team_time = (EditText) findViewById(R.id.writing_team_time);
        writing_team_field = (EditText) findViewById(R.id.writing_team_field);
        writing_team_money = (EditText) findViewById(R.id.writing_team_money);
        writing_team_story = (EditText) findViewById(R.id.writing_team_story);

        team_mon = (CheckBox) findViewById(R.id.team_mon);
        team_tue = (CheckBox) findViewById(R.id.team_tue);
        team_wed = (CheckBox) findViewById(R.id.team_wed);
        team_thu = (CheckBox) findViewById(R.id.team_thu);
        team_fri = (CheckBox) findViewById(R.id.team_fri);
        team_sat = (CheckBox) findViewById(R.id.team_sat);
        team_sun = (CheckBox) findViewById(R.id.team_sun);


        /** Firebase Store 초기화 **/
        db = FirebaseFirestore.getInstance();



        writing_team_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        writing_team_complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                team_title = writing_team_title.getText().toString().trim();
                team_time = writing_team_time.getText().toString().trim();
                team_field = writing_team_field.getText().toString().trim();
                team_money = writing_team_money.getText().toString().trim();
                team_story = writing_team_story.getText().toString().trim();

                if(team_title.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(team_time.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"시간을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(team_field.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"구장 이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(team_money.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"회비 정보를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(team_story.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"팀 설명을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(!(team_mon.isChecked() | team_tue.isChecked() | team_wed.isChecked() |
                        team_thu.isChecked() | team_fri.isChecked() | team_sat.isChecked() | team_sun.isChecked()))
                {
                    Toast.makeText(getApplicationContext(),"요일을 하나 이상 체크해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    /** Team 게시물 저장 **/
                    setDayText();
                    saveTeamPost();
                    finish();
                }
            }

            
            /** 요일 checkbox 정보 String 으로 변환 **/
            private void setDayText() {
                if(team_mon.isChecked()) {
                        days = days + "월";
                }
                if(team_tue.isChecked()) {
                    if(days.equals("")) { days = days + "화";}
                    else { days = days + ", 화"; }
                }
                if(team_wed.isChecked()) {
                    if(days.equals("")) { days = days + "수";}
                    else { days = days + ", 수"; }
                }
                if(team_thu.isChecked()) {
                    if(days.equals("")) { days = days + "목";}
                    else { days = days + ", 목"; }
                }
                if(team_fri.isChecked()) {
                    if(days.equals("")) { days = days + "금";}
                    else { days = days + ", 금"; }
                }
                if(team_sat.isChecked()) {
                    if(days.equals("")) { days = days + "토";}
                    else { days = days + ", 토"; }
                }
                if(team_sun.isChecked()) {
                    if(days.equals("")) { days = days + "일";}
                    else { days = days + ", 일"; }
                }

            }

            /** Team 게시물 저장 **/
            private void saveTeamPost() {

                // Create a new team_post with a first and last name
                Map<String, Object> team_post = new HashMap<>();
                team_post.put("roll","team");
                team_post.put("title", team_title);
                team_post.put("time", team_time);
                team_post.put("field", team_field);
                team_post.put("money", team_money);
                team_post.put("story", team_story);
                team_post.put("days", days);
                team_post.put("name", name);
                team_post.put("email", email);

                // Add a new document with a generated ID
                db.collection(region)
                        .add(team_post)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("팀 게시물 저장 성공", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("팀 게시물 저장 실패", "Error adding document", e);
                            }
                        });
            }

        });


    }
}
