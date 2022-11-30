package com.example.uridongnefc;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private TextView main_region_txt;
    private ArrayList<PostItem> dataList;
    private Button writing_btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_main);

        /** 회원가입 or 로그인에서 사용자 region String 값을 Intent 전달을 통해 받아와야 함 **/

        RecyclerView recyclerView = findViewById(R.id.eggong_chat_recycler_view);

        LinearLayoutManager manager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        PostItem postItem = new PostItem(
                "가좌FC",
                "축구에 열정 있으신 분들 오세요!",
                "요일 : 토, 일",
                "오후 4시 ~ 6시",
                ViewType.TEAM_ACCOUNT);

        dataList.add(postItem);

        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        recyclerView.setAdapter(new MyAdapter(dataList));  // Adapter 등록


//        PostItem item = new PostItem(input_content, "tester", timeStamp, ViewType.RIGHT_CHAT);
//        dataList.add(item);


    }
}