package com.bloodArranger.bloodarranger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Home_Donor extends AppCompatActivity {
    ArrayList<String> uname, name;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_donor);
        setTitle("Donor");
        getSupportActionBar().hide();


        uname = new ArrayList<String>();
        name = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                name
        );

        ListView myListView = findViewById(R.id.myListView_DH);
        myListView.setAdapter(adapter);

        Intent data = getIntent();
        String email = data.getStringExtra("email");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Stock");


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String key = dataSnapshot.getKey();
                String namee = dataSnapshot.child("name").getValue(String.class);
//                Long quantity = dataSnapshot.child("Quantity").getValue(Long.class);
                name.add(namee);
                uname.add(key);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String getUN = uname.get(position),
                        namea = name.get(position);
                Toast.makeText(Home_Donor.this, "Clicked On: "+ getUN, Toast.LENGTH_SHORT).show();
                Intent hD = new Intent(
                        Home_Donor.this,
                        HospitalDetails.class
                );
                hD.putExtra("email", email);
                hD.putExtra("username", getUN);
                hD.putExtra("activity", "Donor");
                hD.putExtra("name",namea);
                startActivity(hD);

            }
        });




        ImageView notif = findViewById(R.id.Notif_DH),
                sett = findViewById(R.id.Setting_DH);
        Button btnReq = findViewById(R.id.sendR_DH);

        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(
                        Home_Donor.this,
                        NotificationPage.class
                );
                n.putExtra("email",email);
                n.putExtra("activity", "Donor");
                startActivity(n);

            }
        });

        sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(
                        Home_Donor.this,
                        SettingPage.class
                );
                s.putExtra("email", email);
                s.putExtra("activity", "Donor");
                startActivity(s);
            }
        });


        btnReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(
                        Home_Donor.this,
                        SendRequest_D.class
                );
                n.putExtra("email",email);
                startActivity(n);
            }
        });
    }
}