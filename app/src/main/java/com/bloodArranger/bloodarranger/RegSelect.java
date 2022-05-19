package com.bloodArranger.bloodarranger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegSelect extends AppCompatActivity {
    Button D_Rreg;
    Button H_Reg;
    TextView backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_select);
        getSupportActionBar().hide();

        D_Rreg = findViewById(R.id.s_dr_Reg);
        H_Reg = findViewById(R.id.s_h_Reg);
        backbutton = findViewById(R.id.signIn_selectReg);

        D_Rreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(RegSelect.this, DonorSignup.class);
                startActivity(intent1);
            }
        });

        H_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(RegSelect.this, HospitalRegistration.class);
                startActivity(intent2);
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegSelect.this, LoginScreen.class);
                startActivity(intent);
            }
        });

    }
}