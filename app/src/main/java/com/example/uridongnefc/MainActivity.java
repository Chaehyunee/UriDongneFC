package com.example.uridongnefc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    /** Firebase Store result **/
    private Map<String, Object> result;

    private TextView main_region_txt;
    private ArrayList<PostItem> dataList;
    private Button writing_btn;

    private int current_user_roll;
    private String region;
    private String email;

    /** Firebase Store Setting **/
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        /** 이전 화면에서 email 받아오기 **/
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        /** Firebase Store 초기화 **/
        db = FirebaseFirestore.getInstance();

        /** DB 내용 받아오기 **/
        Executor executor = command -> {
            Thread thread = new Thread(command);
            thread.start();
        };

        DocumentReference docRef1 = db.collection("users_info").document(email);

        /** Firebase Store 에서 사용자 region String, roll int 값 받아오기 **/
        docRef1.get().addOnCompleteListener(executor, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        result = document.getData();
                        region = (String) result.get("region");
                        current_user_roll = (int) result.get("roll");
                        Log.d("test result : ", region);
                    }
                }
            }
        });


        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);

        LinearLayoutManager manager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        PostItem postItem = new PostItem(
                "test 가좌FC",
                "축구에 열정 있으신 분들 오세요!",
                "요일 : 토, 일",
                "오후 4시 ~ 6시",
                ViewType.TEAM_ACCOUNT);

        dataList.add(postItem);

        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        recyclerView.setAdapter(new MyAdapter(dataList));  // Adapter 등록



        /** 글 작성 버튼 **/
        writing_btn = (Button) findViewById(R.id.writing_btn);

        writing_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_user_roll > 0){ //roll -> player : 1, team : 0
                    Intent intent = new Intent(MainActivity.this, WritingPlayerActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, WritingTeamActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}