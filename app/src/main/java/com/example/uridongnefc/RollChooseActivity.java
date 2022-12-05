package com.example.uridongnefc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class RollChooseActivity  extends Activity {

    private Button team_btn;
    private Button player_btn;
    private String region;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.roll_choose_activity);

        /** 동네 주소 받아오기 **/
        Intent intent=getIntent();
        region = intent.getStringExtra("region");

        team_btn = (Button) findViewById(R.id.roll_choice_team);
        player_btn = (Button) findViewById(R.id.roll_choice_player);

        team_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RollChooseActivity.this, RegistTeamActivity.class);
                intent.putExtra("region", region);
                startActivity(intent);
                finish();
            }
        });

        player_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RollChooseActivity.this, RegistPlayerActivity.class);
                intent.putExtra("region", region);
                startActivity(intent);
                finish();
            }
        });

    }
}
