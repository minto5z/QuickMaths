package com.learn.alaminahmed.quickmaths;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CongratulationActivity extends AppCompatActivity {

    private TextView congoText;
    private Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation);

        Bundle bundle = getIntent().getExtras();
        String result = bundle.getString("key");

        congoText = findViewById(R.id.congoText);
        congoText.setText("scored "+result+" points.");

        okBtn = findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CongratulationActivity.this,HomeActivity.class));
                kill_activity();
            }
        });

    }

    void kill_activity()
    {
        finish();
    }
}
