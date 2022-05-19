package com.bloodArranger.bloodarranger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");
        Intent gdata = getIntent();
        String em = gdata.getStringExtra("email");
        String n = gdata.getStringExtra("activity");

        Button btnBack = findViewById(R.id.back_Prof);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(
                        Profile.this,
                        SettingPage.class
                );
                b.putExtra("email", em);
                b.putExtra("activity", n);
                startActivity(b);
            }
        });

        TextView name = findViewById(R.id.name), email = findViewById(R.id.email),
                type = findViewById(R.id.type), username = findViewById(R.id.username),
                phone = findViewById(R.id.phone);


        DatabaseReference myRef = database.getReference("Users").child(em);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String n = dataSnapshot.child("name").getValue(String.class);
                String t = dataSnapshot.child("userType").getValue(String.class),
                        e = dataSnapshot.child("email").getValue(String.class),
                        un = dataSnapshot.child("username").getValue(String.class),
                        ph = dataSnapshot.child("phone").getValue(String.class);

                name.setText(n);
                email.setText(e);
                type.setText(t);
                username.setText(un);
                phone.setText(ph);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}