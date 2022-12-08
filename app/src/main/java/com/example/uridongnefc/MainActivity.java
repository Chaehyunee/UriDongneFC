package com.example.uridongnefc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    /** Firebase Store result **/
    private Map<String, Object> result;

    private TextView main_region_txt;
    private ArrayList<PostItem> dataList;
    private FloatingActionButton writing_btn;

    private String current_user_roll;
    private String region;
    private String email;

    /** Thread 사용 **/
    Handler mHandler = null;

    /** Firebase Store Setting **/
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        /** Thread 관련 **/
        mHandler = new Handler();

        /** 이전 화면에서 email 받아오기 **/
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        Log.d("test result email : ", email);

        /** Firebase Store 초기화 **/
        db = FirebaseFirestore.getInstance();

        /** DB 내용 받아오기 **/
        Executor executor = command -> {
            Thread thread = new Thread(command);
            thread.start();
        };


        /** Firebase Store 에서 사용자 region String, roll int 값 받아오기 **/
        CollectionReference colRef = db.collection("users_info").document(email).collection(email);

        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            result = document.getData();
                            region = (String) result.get("region");
                            current_user_roll = (String) result.get("roll");
                            Log.d("region, roll 받아오기", region + " & " + current_user_roll);

                        }
                    }
                }else {
                        Log.d("region, roll 받아오기", "실패");
                }
            }
        });




        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);


        LinearLayoutManager manager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

/*        PostItem postItem = new PostItem(
                "test 가좌FC",
                "축구에 열정 있으신 분들 오세요!",
                "요일 : 토, 일",
                "오후 4시 ~ 6시",
                ViewType.TEAM_ACCOUNT);

        dataList.add(postItem);

        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        recyclerView.setAdapter(new MyAdapter(dataList));  // Adapter 등록

*/


        /** 글 작성 버튼 **/
        writing_btn = (FloatingActionButton) findViewById(R.id.writing_btn);

        writing_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_user_roll.equals("player")){ //roll -> player : 1, team : 0
                    Intent intent = new Intent(MainActivity.this, WritingPlayerActivity.class);
                    intent.putExtra("region", region);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MainActivity.this, WritingTeamActivity.class);
                    intent.putExtra("region", region);
                    startActivity(intent);
                }
            }
        });
    }
}