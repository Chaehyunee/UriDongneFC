package com.example.uridongnefc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WritingPlayerActivity extends AppCompatActivity {

    /** page 이동 버튼 **/
    private ImageView writing_player_back_btn;
    private ImageView writing_player_complete_btn;

    /** team 게시글 작성 내용 **/
    private EditText writing_player_title;
    private EditText writing_player_time;
    private EditText writing_player_age;
    private Spinner player_position_spinner;
    private TextView player_position_textview;
    private EditText writing_player_story;

    private String player_title;
    private String player_time;
    private String player_age;
    private String player_position = "";
    private String player_story;


    /** team 요일 체크박스 **/
    private CheckBox player_mon;
    private CheckBox player_tue;
    private CheckBox player_wed;
    private CheckBox player_thu;
    private CheckBox player_fri;
    private CheckBox player_sat;
    private CheckBox player_sun;
    private String days = "";

    /** position spinner setting **/
    final String[] positions = {"골키퍼 (GK)", "수비수 (DF)", "미드필더 (MF)", "공격수 (FW)"};

    /** 지역명 받아오기 **/
    private String region;


    /** Firebase Authentication Setting **/
    private FirebaseFirestore db;

    private List<String> listview_items;
    private ArrayAdapter<String> listview_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_player_activity);

        /** 이전 화면에서 region 받아오기 **/
        Intent intent = getIntent();
        region = intent.getStringExtra("region");


        writing_player_back_btn = (ImageView) findViewById(R.id.writing_player_back_btn);
        writing_player_complete_btn = (ImageView) findViewById(R.id.writing_player_complete_btn);

        writing_player_title = (EditText) findViewById(R.id.writing_player_title);
        writing_player_time = (EditText) findViewById(R.id.writing_player_time);
        writing_player_age = (EditText) findViewById(R.id.writing_player_age);
        writing_player_story = (EditText) findViewById(R.id.writing_player_story);


        /** spinner Initialize **/
        player_position_spinner = (Spinner) findViewById(R.id.player_position_spinner);
        player_position_textview = (TextView) findViewById(R.id.player_position_textview);


        /** 스피너와 리스트를 연결하기 위해 사용되는 어댑터 **/
        ArrayAdapter<String> spinner_adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, positions);


        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //스피너의 어댑터 지정
        player_position_spinner.setAdapter(spinner_adapter);


        player_position_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str = adapterView.getItemAtPosition(i).toString();

                if ( str != "")
                    player_position = str;
                    Log.d("test", "success" + str);
                    player_position_textview.setText(player_position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /** 요일 체크박스 연결 **/
        player_mon = (CheckBox) findViewById(R.id.player_mon);
        player_tue = (CheckBox) findViewById(R.id.player_tue);
        player_wed = (CheckBox) findViewById(R.id.player_wed);
        player_thu = (CheckBox) findViewById(R.id.player_thu);
        player_fri = (CheckBox) findViewById(R.id.player_fri);
        player_sat = (CheckBox) findViewById(R.id.player_sat);
        player_sun = (CheckBox) findViewById(R.id.player_sun);


        /** Firebase Store 초기화 **/
        db = FirebaseFirestore.getInstance();

        writing_player_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        writing_player_complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                player_position = player_position_spinner.getSelectedItem().toString();

                /** Edit text to String **/
                player_title = writing_player_title.getText().toString().trim();
                player_time = writing_player_time.getText().toString().trim();
                player_age = writing_player_age.getText().toString().trim();
                player_story = writing_player_story.getText().toString().trim();


                if(player_title.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(player_time.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"시간을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(player_age.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"나이를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(player_position.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"희망 포지션을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(player_story.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"선수님의 이야기를 적어주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(!(player_mon.isChecked() | player_tue.isChecked() | player_wed.isChecked() |
                        player_thu.isChecked() | player_fri.isChecked() | player_sat.isChecked() | player_sun.isChecked()))
                {
                    Toast.makeText(getApplicationContext(),"요일을 하나 이상 체크해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    /** Player 게시물 저장 **/
                    setDayText();
                    savePlayerPost();
                    finish();
                }
            }


            /** 요일 checkbox 정보 String 으로 변환 **/
            private void setDayText() {
                if(player_mon.isChecked()) {
                    days = days + "월";
                }
                if(player_tue.isChecked()) {
                    if(days.equals("")) { days = days + "화";}
                    else { days = days + ", 화"; }
                }
                if(player_wed.isChecked()) {
                    if(days.equals("")) { days = days + "수";}
                    else { days = days + ", 수"; }
                }
                if(player_thu.isChecked()) {
                    if(days.equals("")) { days = days + "목";}
                    else { days = days + ", 목"; }
                }
                if(player_fri.isChecked()) {
                    if(days.equals("")) { days = days + "금";}
                    else { days = days + ", 금"; }
                }
                if(player_sat.isChecked()) {
                    if(days.equals("")) { days = days + "토";}
                    else { days = days + ", 토"; }
                }
                if(player_sun.isChecked()) {
                    if(days.equals("")) { days = days + "일";}
                    else { days = days + ", 일"; }
                }

            }

            /** Player 게시물 저장 **/
            private void savePlayerPost() {

                // Create a new team_post with a first and last name
                Map<String, Object> team_post = new HashMap<>();
                team_post.put("roll","player");
                team_post.put("title", player_title);
                team_post.put("time", player_time);
                team_post.put("age", player_age);
                team_post.put("position", player_position);
                team_post.put("story", player_story);
                team_post.put("days", days);

                // Add a new document with a generated ID
                db.collection(region)
                        .add(team_post)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("선수 게시물 저장 성공", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("선수 게시물 저장 실패", "Error adding document", e);
                            }
                        });
            }

        });


    }

/*    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("WritingPlayerActivity", "onItemSelected");
        adapterView.getItemAtPosition(i);
        player_position = positions[i];
        Toast.makeText(getApplicationContext(),
                        positions[i],Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d("WritingPlayerActivity", "onNothingSelected");
        player_position = positions[3]; //default : 공격수
    }*/
}
