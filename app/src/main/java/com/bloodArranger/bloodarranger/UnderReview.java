package com.bloodArranger.bloodarranger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UnderReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_review);
        getSupportActionBar().hide();


        Intent i = getIntent();
        Toast.makeText(UnderReview.this, i.getStringExtra("email"), Toast.LENGTH_SHORT).show();

        Button btn1 = findViewById(R.id.Login_Aproval),
                btn2 = findViewById(R.id.ContactUs_Approval);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(
                        UnderReview.this, LoginScreen.class
                );

                startActivity(l);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(
                        UnderReview.this, ContactMe.class
                );
                c.putExtra("activity", "underReview");
                startActivity(c);
            }
        });

    }
}