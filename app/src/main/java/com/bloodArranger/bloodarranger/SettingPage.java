package com.bloodArranger.bloodarranger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);
//        getSupportActionBar().hide();

        setTitle("Settings");

        Intent data = getIntent();
        String email = data.getStringExtra("email");

        Button logout, contact, about, profile, back;
        TextView tv_back;

        logout = findViewById(R.id.logout_Setting);
        back = findViewById(R.id.back_btn_sett);
        contact = findViewById(R.id.Contact_Setting);
        about = findViewById(R.id.AboutUs_Setting);
        profile = findViewById(R.id.profile_Setting);
//        tv_back = findViewById(R.id.SettingsBack);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lo = new Intent(
                        SettingPage.this,
                        LoginScreen.class
                );
                startActivity(lo);
            }
        });
        String n = data.getStringExtra("activity");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (n.equals("Donor")){
                    Intent b = new Intent(
                            SettingPage.this,
                            Home_Donor.class
                    );
                    b.putExtra("email", email);
                    startActivity(b);
                }
                else{
                    Intent b = new Intent(
                            SettingPage.this,
                            Home_Hospital.class
                    );
                    b.putExtra("email", email);
                    startActivity(b);
                }

//                try {
//                    Intent b = new Intent(
//                            SettingPage.this,
//                            Class.forName(n)
//                    );
//                    b.putExtra("email", email);
//                    startActivity(b);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(
                        SettingPage.this,
                        ContactMe.class
                );
                c.putExtra("email", email);
                c.putExtra("activity", n);
                startActivity(c);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(
                        SettingPage.this,
                        AboutUs.class
                );
                c.putExtra("activity", n);

                c.putExtra("email", email);
                startActivity(c);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(
                        SettingPage.this,
                        Profile.class
                );
                c.putExtra("activity", n);
                c.putExtra("email", email);
                startActivity(c);
            }
        });
//
//        tv_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String n = data.getStringExtra("activity");
//                try {
//                    Intent b = new Intent(
//                            SettingsPage.this,
//                            Class.forName(n)
//                    );
//                    b.putExtra("email", email);
//                    startActivity(b);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}