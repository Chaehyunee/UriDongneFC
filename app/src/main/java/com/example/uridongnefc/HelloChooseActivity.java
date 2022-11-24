package com.example.uridongnefc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class HelloChooseActivity extends Activity {

    private Button yes_btn;
    private Button no_btn;

    private String user_address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hello_choose_activity);

        Intent intent=getIntent();
        user_address = intent.getStringExtra("user_address");

        yes_btn = (Button) findViewById(R.id.yes_button);
        no_btn = (Button) findViewById(R.id.no_button);

        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelloChooseActivity.this, RegionChooseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelloChooseActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
