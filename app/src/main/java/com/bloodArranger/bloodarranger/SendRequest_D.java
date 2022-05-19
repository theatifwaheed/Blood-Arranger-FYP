package com.bloodArranger.bloodarranger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SendRequest_D extends AppCompatActivity {
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
        setContentView(R.layout.activity_send_request_d);
        getSupportActionBar().hide();


        Intent getData = getIntent();
        String email = getData.getStringExtra("email");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child(email).child("name").getValue(String.class);
                Phone = dataSnapshot.child(email).child("phone").getValue(String.class);
                Toast.makeText(SendRequest_D.this, "Data Got", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        EditText lo = findViewById(R.id.et_Long),
                la = findViewById(R.id.et_Lat),
                ad = findViewById(R.id.et_Address),
                c = findViewById(R.id.et_City);



        ImageView goBack = findViewById(R.id.back_DR);
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
                    Toast.makeText(SendRequest_D.this, "Select Blood Group", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(SendRequest_D.this, "Select Blood Group", Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        EditText lon = findViewById(R.id.et_Long), lat = findViewById(R.id.et_Lat);
        Button btnSend = findViewById(R.id.Send_Req_D);
//        TextView tv_Back = findViewById(R.id.tv_back_Req_D);

//        Intent data = getIntent();
//        String email = data.getStringExtra("email");

        DatabaseReference myRef2 = database.getReference("Notification");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                longitude = lo.getText().toString();
                latitude = la.getText().toString();
                city = c.getText().toString();
                Address = ad.getText().toString();

                if (SelectedBG == true && !longitude.isEmpty() && !latitude.isEmpty() && !city.isEmpty() && !Address.isEmpty()){
//                    myRef2.child(email).child("email").child(mail);
                    myRef2.child(email).child("BG").setValue(BG);
                    myRef2.child(email).child("username").setValue(email);
                    myRef2.child(email).child("name").setValue(name);
                    myRef2.child(email).child("Address").setValue(Address);
                    myRef2.child(email).child("long").setValue(longitude);
                    myRef2.child(email).child("lat").setValue(latitude);
                    myRef2.child(email).child("city").setValue(city);
                    myRef2.child(email).child("phone").setValue(Phone);


                    Toast.makeText(SendRequest_D.this, "Request Sent", Toast.LENGTH_SHORT).show();
                    Intent n = new Intent(
                            SendRequest_D.this,
                            Home_Donor.class
                    );
                    n.putExtra("email", email);
                    startActivity(n);


                }
                else   {
                    Toast.makeText(SendRequest_D.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nn = new Intent(
                        SendRequest_D.this,
                        Home_Donor.class
                );
                nn.putExtra("email", email);
                startActivity(nn);
            }
        });

    }
}