package com.bloodArranger.bloodarranger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestReceived extends AppCompatActivity {
    TextView bgtv, addresstv,citytv,phonetv,nametv;
    String Phone;
    String mapString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_received);
        getSupportActionBar().hide();


        FirebaseDatabase database = FirebaseDatabase.getInstance();


        Intent getData = getIntent();
        String email = getData.getStringExtra("email");
        String username = getData.getStringExtra("username");
        String act = getData.getStringExtra("activity");
//        String name = getData.getStringExtra("name");

        ImageView goback = findViewById(R.id.goBack_RR);
        Button btnBack = findViewById(R.id.buttonBack_RR);
        bgtv = findViewById(R.id.BG_RR);
        addresstv = findViewById(R.id.address_TV_RR);
        citytv = findViewById(R.id.city_TV_RR);
        phonetv = findViewById(R.id.phone_TV_RR);
        nametv = findViewById(R.id.name_TV_RR);



        DatabaseReference myRef = database.getReference("Notification").child(username);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String name = dataSnapshot.child("name").getValue(String.class);
                String bg = dataSnapshot.child("BG").getValue(String.class);
                String city = dataSnapshot.child("city").getValue(String.class);
                Phone = dataSnapshot.child("phone").getValue(String.class);
                String address = dataSnapshot.child("Address").getValue(String.class);
                String lon = dataSnapshot.child("long").getValue(String.class);
                String lat = dataSnapshot.child("lat").getValue(String.class);

                mapString = "https://www.google.com/maps/dir//" +
                        lat + "%2F" + lon + "/";


                nametv.setText(name);
                bgtv.setText(bg);
                citytv.setText(city);
                addresstv.setText(address);
                phonetv.setText(Phone);

//                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(
                        RequestReceived.this,
                        NotificationPage.class

                );
                n.putExtra("activity", act);
                n.putExtra("email", email);
                startActivity(n);
            }
        });


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(
                        RequestReceived.this,
                        NotificationPage.class

                );
                n.putExtra("activity", act);
                n.putExtra("email", email);
                startActivity(n);
            }
        });

        Button btnCall = findViewById(R.id.buttonBack_RR);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = "tel: " + Phone;
                if (ActivityCompat.checkSelfPermission(RequestReceived.this, Manifest.permission.CALL_PHONE) ==
                        PackageManager.PERMISSION_GRANTED){

                    RequestReceived.this.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(temp)));
                    finish();
                }
            }
        });



        ImageView locc = findViewById(R.id.location_RR);
        locc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mapString));
                startActivity(i);
            }
        });



    }
}