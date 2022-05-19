package com.bloodArranger.bloodarranger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SendRequest_H extends AppCompatActivity {
    Spinner spinner_selectBG;
    int pos;
//    String[] BG_Name = { "Select Blood Group", "AP", "AN" , "BP",
//            "BN" , "OP", "ON" , "ABP", "ABN"};
    String[] BG_Name = { "Select Blood Group", "A+", "A-" , "B+",
            "B-" , "O+", "O-" , "AB+", "AB-"};
    String BG;
    boolean SelectedBG = false;
    String name, Address, Phone, longitude, latitude, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request_h);

        getSupportActionBar().hide();
        Intent getData = getIntent();
        String email = getData.getStringExtra("email");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                    mail = dataSnapshot.child(email).child("email").getValue(String.class);
                    name = dataSnapshot.child(email).child("name").getValue(String.class);
                    Address = dataSnapshot.child(email).child("address").getValue(String.class);
                    Phone = dataSnapshot.child(email).child("phone").getValue(String.class);
                    longitude = dataSnapshot.child(email).child("long").getValue(String.class);
                    city = dataSnapshot.child(email).child("city").getValue(String.class);
                    latitude = dataSnapshot.child(email).child("lat").getValue(String.class);

                Toast.makeText(SendRequest_H.this, "Data Got", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });




        ImageView goBack = findViewById(R.id.back_HR);
        spinner_selectBG = findViewById(R.id.spin_selectImage);


//        spinner_selectBG = findViewById(R.id.spin_selectImage);
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
                    Toast.makeText(SendRequest_H.this, "Select Blood Group", Toast.LENGTH_SHORT).show();
                } else if(position==1) {
                    BG = BG_Name[1];
                    SelectedBG = true;
                } else if(position==2) {
                    BG = BG_Name[2];
                    SelectedBG = true;
                } else if(position==3) {
                    BG = BG_Name[3];
                    SelectedBG = true;
                } else if(position==4) {
                    BG = BG_Name[4];
                    SelectedBG = true;
                }
                else if(position==5) {
                    BG = BG_Name[5];
                    SelectedBG = true;
                }
                else if(position==6) {
                    BG = BG_Name[6];
                    SelectedBG = true;
                }
                else if(position==7) {
                    BG = BG_Name[7];
                    SelectedBG = true;
                }
                else if(position==8) {
                    BG = BG_Name[8];
                    SelectedBG = true;
                }
                else {
                    Toast.makeText(SendRequest_H.this, "Select Blood Group", Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        EditText lon = findViewById(R.id.et_Long), lat = findViewById(R.id.et_Lat);
        Button btnSend = findViewById(R.id.Send_Req_H);
//        TextView tv_Back = findViewById(R.id.tv_back_Req_D);

//        Intent data = getIntent();
//        String email = data.getStringExtra("email");

        DatabaseReference myRef2 = database.getReference("Notification");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectedBG == true){
//                    myRef2.child(email).child("email").child(mail);
                    myRef2.child(email).child("BG").setValue(BG);
                    myRef2.child(email).child("username").setValue(email);
                    myRef2.child(email).child("name").setValue(name);
                    myRef2.child(email).child("Address").setValue(Address);
                    myRef2.child(email).child("long").setValue(longitude);
                    myRef2.child(email).child("lat").setValue(latitude);
                    myRef2.child(email).child("city").setValue(city);
                    myRef2.child(email).child("phone").setValue(Phone);


                    Toast.makeText(SendRequest_H.this, "Request Sent", Toast.LENGTH_SHORT).show();
                    Intent n = new Intent(
                            SendRequest_H.this,
                            Home_Hospital.class
                    );
                    n.putExtra("email", email);
                    startActivity(n);


                }
                else   {
                    Toast.makeText(SendRequest_H.this, "Select Blood Group", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nn = new Intent(
                        SendRequest_H.this,
                        Home_Hospital.class
                );
                nn.putExtra("email", email);
                startActivity(nn);
            }
        });

    }
}