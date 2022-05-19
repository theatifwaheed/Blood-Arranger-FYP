package com.bloodArranger.bloodarranger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setTitle("About Us");

        TextView tv_Back = findViewById(R.id.back_About);
        Button btn_back = findViewById(R.id.back_About);

        Intent data = getIntent();
        String d = data.getStringExtra("email");
        String n = data.getStringExtra("activity");

        tv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(
                        AboutUs.this,
                        SettingPage.class
                );
                back.putExtra("email", d);
                back.putExtra("activity", n);
                startActivity(back);

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(
                        AboutUs.this,
                        SettingPage.class
                );
                back.putExtra("email", d);
                back.putExtra("activity", n);
                startActivity(back);
            }
        });
    }
}