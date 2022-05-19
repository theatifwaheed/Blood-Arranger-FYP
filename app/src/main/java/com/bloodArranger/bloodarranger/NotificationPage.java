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

public class NotificationPage extends AppCompatActivity {
    ArrayList<String> uname, name, title;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);
        getSupportActionBar().hide();



        uname = new ArrayList<String>();
        name = new ArrayList<String>();
        title = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                title
        );

        ListView myListView = findViewById(R.id.myListView_Notif);
        myListView.setAdapter(adapter);

        Intent data = getIntent();
        String email = data.getStringExtra("email");
        String act = data.getStringExtra("activity");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Notification");


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String key = dataSnapshot.getKey();
                String namee = dataSnapshot.child("name").getValue(String.class);
                String city = dataSnapshot.child("city").getValue(String.class);
                String bg = dataSnapshot.child("BG").getValue(String.class);
//                Long quantity = dataSnapshot.child("Quantity").getValue(Long.class);
                String add = bg + " : " + city + " : " + namee;
                title.add(add);
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
                Toast.makeText(NotificationPage.this, "Clicked On: "+ getUN, Toast.LENGTH_SHORT).show();
//                Intent hD = new Intent(
//                        NotificationPage.this,
//                        HospitalDetails.class
//                );


                Intent n = new Intent(
                        NotificationPage.this,
                        RequestReceived.class
                );
                n.putExtra("email", email);
                n.putExtra("activity", act);
                n.putExtra("username", getUN);
                n.putExtra("name", namea);
                startActivity(n);
            }
        });


        ImageView back = findViewById(R.id.backButton_N);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (act.equals("Hospital")){
                    Intent n = new Intent(
                            NotificationPage.this,
                            Home_Hospital.class
                    );
                    n.putExtra("email", email);
                    startActivity(n);
                } else if (act.equals("Donor")){
                    Intent n = new Intent(
                            NotificationPage.this,
                            Home_Donor.class
                    );
                    n.putExtra("email", email);
                    startActivity(n);
                }
            }
        });
        Button goback = findViewById(R.id.goBack_Notif);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (act.equals("Hospital")){
                    Intent n = new Intent(
                            NotificationPage.this,
                            Home_Hospital.class
                    );
                    n.putExtra("email", email);
                    startActivity(n);
                } else if (act.equals("Donor")){
                    Intent n = new Intent(
                            NotificationPage.this,
                            Home_Donor.class
                    );
                    n.putExtra("email", email);
                    startActivity(n);
                }
            }
        });





    }
}