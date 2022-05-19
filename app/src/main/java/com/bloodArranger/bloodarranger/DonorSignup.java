package com.bloodArranger.bloodarranger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonorSignup extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    Spinner spinner_selectBG;
    int pos;
    String[] BG_Name = { "Select Blood Group", "A+", "A-" , "B+",
            "B-" , "O+", "O-" , "AB+", "AB-"};
    String BG;
    boolean selectSpin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_signup);
        getSupportActionBar().hide();
        String type = "Donor";


        Button btnRegister = findViewById(R.id.Reg_d_Reg);
        TextView tv_Login = findViewById(R.id.signIn_d_Reg);

        tv_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(
                        DonorSignup.this,
                        LoginScreen.class
                );
                startActivity(n);
            }
        });

        EditText name = findViewById(R.id.name_d_Reg), email = findViewById(R.id.email_d_Reg),
                username = findViewById(R.id.username_d_RG),
                pass = findViewById(R.id.password_d_Reg), phone = findViewById(R.id.phone_d_Reg);


        spinner_selectBG = findViewById(R.id.BGS_RD);
        ArrayAdapter imagesAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, BG_Name);
        imagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_selectBG.setAdapter(imagesAdapter);
        spinner_selectBG.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(InsertDataActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                pos = position;
                if (position==0) {
                    Toast.makeText(DonorSignup.this, "Select Blood Group", Toast.LENGTH_SHORT).show();
                } else if(position==1) {
                    BG = BG_Name[1];
                    selectSpin = true;
                } else if(position==2) {
                    BG = BG_Name[2];
                    selectSpin = true;
                } else if(position==3) {
                    BG = BG_Name[3];
                    selectSpin = true;
                } else if(position==4) {
                    BG = BG_Name[4];
                    selectSpin = true;
                }
                else if(position==5) {
                    BG = BG_Name[5];
                    selectSpin = true;
                }
                else if(position==6) {
                    BG = BG_Name[6];
                    selectSpin = true;
                }
                else if(position==7) {
                    selectSpin = true;
                    BG = BG_Name[7];
                }
                else if(position==8) {
                    BG = BG_Name[8];
                    selectSpin = true;
                }
                else {
                    Toast.makeText(DonorSignup.this, "Select Blood Group", Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString(), e = email.getText().toString(),
                        un = username.getText().toString(),
                        p = pass.getText().toString(), ph = phone.getText().toString();
                if (n.equals("") || e.equals("") || p.equals("") || ph.equals("") || un.equals("")){
                    Toast.makeText(DonorSignup.this, "Please Fill all of the Above", Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                    ref.orderByChild("username").equalTo(un).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(DonorSignup.this, "Username Exists. Please use another username", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if (passLen(p) && selectSpin == true) {
                                    DatabaseReference addData = database.getReference("Users");
                                    addData.child(un).child("name").setValue(n);
                                    addData.child(un).child("email").setValue(e);
                                    addData.child(un).child("username").setValue(un);
                                    addData.child(un).child("pass").setValue(p);
                                    addData.child(un).child("phone").setValue(ph);
                                    addData.child(un).child("userType").setValue(type);
                                    addData.child(un).child("BG").setValue(BG);

                                    Toast.makeText(DonorSignup.this, "You've been Signed Up. Login to your Account.", Toast.LENGTH_SHORT).show();

//                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    Intent login = new Intent(
                                            DonorSignup.this,
                                            LoginScreen.class
                                    );
                                    startActivity(login);
                                }
                                else  {
                                    Toast.makeText(DonorSignup.this, "Please Fill All of the Fields.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


    }

    private boolean passLen(String p){
        if (p.length() > 5){
            return true;
        }
        else{
            Toast.makeText(this, "Password Length must be more than 5", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}