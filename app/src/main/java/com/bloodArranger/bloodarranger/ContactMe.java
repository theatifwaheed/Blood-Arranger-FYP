package com.bloodArranger.bloodarranger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContactMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_me);
        getSupportActionBar().hide();


        Intent getBack = getIntent();

        String n = getBack.getStringExtra("activity");

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel: +923451716000"));

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "181854@students.au.edu.pk", null));

        TextView phoneT, emailT;
        Button phoneB, emailB, back;

        phoneB = findViewById(R.id.phone_Button_Contact);
        emailB = findViewById(R.id.email_Button_Contact);
        phoneT = findViewById(R.id.phone_Contact);
        emailT = findViewById(R.id.email_contact);


        phoneB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(callIntent);
            }
        });

        phoneT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(callIntent);
            }
        });

        emailB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(emailIntent);
            }
        });

        emailT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(emailIntent);
            }
        });

        String getD = getBack.getStringExtra("email");
        back = findViewById(R.id.back_contact);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = getIntent();
                String ac = ii.getStringExtra("activity");
                if (ac.equals("underReview")){
                    Intent l = new Intent(
                            ContactMe.this,
                            LoginScreen.class
                    );
                    startActivity(l);
                }
                else {
                    Intent s = new Intent(
                            ContactMe.this,
                            SettingPage.class

                    );
                    s.putExtra("email", getD);
                    s.putExtra("activity", n);
                    startActivity(s);
                }
            }
        });
    }
}