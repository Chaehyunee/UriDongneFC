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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
    private ImageView main_region_change_btn;
    private ImageView main_setting_btn;

    private String current_user_roll;
    private String region;
    private String email;
    private String name;

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

        /** dataList 초기화 **/
        dataList = new ArrayList<>();


        /** 이전 화면에서 email 받아오기 **/
        this.getIntentFromBeforeActivity();


        /** Firebase Store 초기화 **/
        db = FirebaseFirestore.getInstance();

        /** DB 내용 받아오기 **/
        Executor executor = command -> {
            Thread thread = new Thread(command);
            thread.start();
        };

        /** Firebase Store collection 선언 **/
        CollectionReference colRef = db.collection("users_info").document(email).collection(email);

        /** Firebase Store 에서 사용자 region String, roll int 값 받아오기 **/
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.exists()) {
                                            result = document.getData();
                                            region = (String) result.get("region");
                                            name = (String) result.get("name");
                                            current_user_roll = (String) result.get("roll");

                                            Log.d("region, roll 받아오기", region + " & " + name + " & " + current_user_roll);

                                        }
                                    }
                                } else {
                                    Log.d("region, roll 받아오기", "실패");
                                }
                            }
                        });
                    }
                });
            }
        });

        t.start();



        /** 상단 주소명 변경 **/
        main_region_txt = (TextView) findViewById(R.id.main_region_txt);
//        main_region_txt.setText(region);
        main_region_txt.setText("가좌동");


        /** 주소 변경 버튼 ------------ 수정 필요 TODO **/
        main_region_change_btn = (ImageView) findViewById(R.id.main_region_change_btn);
        main_region_change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegionChooseActivity.class);
                intent.putExtra("pre_page", "Main");
                startActivity(intent);
                finish();
            }
        });


        /** 설정 버튼 화면 연결  ------------ 수정 필요 TODO **/
        main_setting_btn = (ImageView) findViewById(R.id.main_setting_btn);



        /** 리사이클러뷰 설정 **/
        RecyclerView ryv = findViewById(R.id.main_recycler_view);


        LinearLayoutManager manager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        ryv.setLayoutManager(manager); // LayoutManager 등록
        ryv.setAdapter(new MyAdapter(dataList));  // Adapter 등록



        /** Recycler View item Data 받아오기 from FireStore **/
        Thread t_ryv = new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        CollectionReference colRef_ryv = db.collection("가좌동"); //TODO collection 경로 고치기
                        colRef_ryv.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Log.w("파이어베이스 ", "리사이클러뷰 연결 실패");
                                    return;
                                }

                                /** dataList 초기화 **/
                                dataList = new ArrayList<>();

                                PostItem postItem;
                            for (QueryDocumentSnapshot doc : value) {
                                if(doc.get("roll") != null) {
                                    String tmp = String.valueOf(doc.get("roll"));

                                    if (tmp.equals("team")) { //팀 계정 글

                                        Log.d("team 계정글", "tmp : " + tmp);
                                        postItem = new PostItem(
                                                doc.getId(),
                                                doc.getString("name"),
                                                doc.getString("title"),
                                                "요일 : " + doc.getString("days"),
                                                "시간대 : " + doc.getString("time"),
                                                ViewType.TEAM_ACCOUNT);

                                    } else { //선수 계정 글
                                        Log.d("player 계정글", "tmp : " + tmp);
                                        postItem = new PostItem(
                                                doc.getId(),
                                                doc.getString("name"),
                                                doc.getString("title"),
                                                "가능한 요일 : " + doc.getString("days"),
                                                "나이 : " + doc.getString("age"), ViewType.PLAYER_ACCOUNT);

                                    }
                                    dataList.add(postItem);
                                    Log.d("파이어베이스", "얻어온 값 : " + postItem.toString());
                                }

                                }

                                /** recyclerview 새로고침 **/
                                MyAdapter adapter = new MyAdapter(dataList);
                                ryv.setAdapter(adapter);
                            }
                        });

                    }
                });
            }
        });

        t_ryv.start();

        /** recyclerview 새로고침 **/
        MyAdapter adapter = new MyAdapter(dataList);


        /** Item 클릭 시 해당 게시물로 연결 **/
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent;
                if (dataList.get(pos).getViewType() == ViewType.TEAM_ACCOUNT) { //TEAM_ACCOUNT
                    intent = new Intent(MainActivity.this, ReadingTeamActivity.class);

                } else {//PLAYER
                    intent = new Intent(MainActivity.this, ReadingPlayerActivity.class);
                }
                intent.putExtra("documentID", dataList.get(pos).getDocumentID());
                intent.putExtra("region", region);
                startActivity(intent);
            }
        });

        ryv.setAdapter(adapter);


        /** 글 작성 버튼 **/
        writing_btn = (FloatingActionButton) findViewById(R.id.writing_btn);

        writing_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_user_roll.equals("player")){ //roll -> player : 1, team : 0
                    Intent intent = new Intent(MainActivity.this, WritingPlayerActivity.class);
                    intent.putExtra("region", region);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MainActivity.this, WritingTeamActivity.class);
                    intent.putExtra("region", region);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            }
        });
    }

    private void getIntentFromBeforeActivity() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        Log.d("test result email : ", email);

    }

}