package com.bloodArranger.bloodarranger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        requestPermissions();
        getSupportActionBar().hide();

        EditText e_Login, p_Login;
        e_Login = findViewById(R.id.email_login);
        p_Login = findViewById(R.id.password_login);
        Button btnLogin = findViewById(R.id.button_Login);
        TextView btnSignup = findViewById(R.id.reg_Login);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(
                        LoginScreen.this,
                        RegSelect.class
                );
//                n.putExtra("email", email);
                startActivity(n);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = e_Login.getText().toString(),
                        pass = p_Login.getText().toString();
                if (email.equals("") || pass.equals("")) {
                    Toast.makeText(LoginScreen.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                    ref.orderByChild("username").equalTo(email).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                //username exist
//                                String p = dataSnapshot.child("pass").getValue(String.class);


                                DatabaseReference myRef = database.getReference("Users").child(email);

                                myRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // This method is called once with the initial value and again
                                        // whenever data at this location is updated.
                                        String ps = dataSnapshot.child("pass").getValue(String.class);
                                        String type = dataSnapshot.child("userType").getValue(String.class);
                                        if (pass.equals(ps)){
                                            Toast.makeText(LoginScreen.this, "Wait for Login...", Toast.LENGTH_SHORT).show();
                                            if (type.equals("Donor")){
                                                Intent d = new Intent(LoginScreen.this, Home_Donor.class);
                                                d.putExtra("email", email);
                                                startActivity(d);
                                            }
                                            else if (type.equals("Hospital")){
                                                String approve = dataSnapshot.child("status").getValue(String.class);
                                                if (approve.equals("Inactive")){
                                                    Intent n = new Intent(
                                                            LoginScreen.this,
                                                            UnderReview.class
                                                    );
                                                    n.putExtra("email", email);
                                                    n.putExtra("act", "underReview");
                                                    startActivity(n);
                                                }
                                                else {
                                                    Intent h = new Intent(
                                                            LoginScreen.this,
                                                            Home_Hospital.class
                                                    );
                                                    h.putExtra("email", email);
                                                    startActivity(h);
                                                }
                                            }
                                        }
                                        else{
                                            Toast.makeText(LoginScreen.this, "Username or Password not Correct", Toast.LENGTH_SHORT).show();
                                        }
//                                        String value = dataSnapshot.child("name").getValue(String.class);
//                                        Toast.makeText(LoginScreen.this, "Value = " + value, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                    }
                                });
                            }
                            else{
                                Toast.makeText(LoginScreen.this, "Username or password Not Correct", Toast.LENGTH_SHORT).show();
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
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_LOGS,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.RECEIVE_SMS


                }, 0);
            }
        }
    }
}

//        DatabaseReference myRef = database.getReference("Users").child("theatifwaheed");
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.child("name").getValue(String.class);
//                Toast.makeText(LoginScreen.this, "Value = " + value, Toast.LENGTH_SHORT).show();
////                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
////                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });