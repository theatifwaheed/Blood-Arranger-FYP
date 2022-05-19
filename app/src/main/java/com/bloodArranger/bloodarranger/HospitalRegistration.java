package com.bloodArranger.bloodarranger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.w3c.dom.Text;

public class HospitalRegistration extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String type = "Hospital";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_registration);
        getSupportActionBar().hide();

        EditText hn = findViewById(R.id.hospName_R),
                ha = findViewById(R.id.hospAddress_HR),
                hc = findViewById(R.id.hospCity_RH),
                he = findViewById(R.id.hospEmail_R),
                hun = findViewById(R.id.hospUsername_R),
                hp = findViewById(R.id.hospPass_R),
                hph = findViewById(R.id.hospPhone_R),
                hlon = findViewById(R.id.hospLong_RH),
                hlat = findViewById(R.id.hospLat_RH);

        Button reg = findViewById(R.id.Reg_HR);
        TextView login = findViewById(R.id.login_HR);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(
                        HospitalRegistration.this,
                        LoginScreen.class
                );
                startActivity(l);
            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = hn.getText().toString(),
                        email = he.getText().toString(),
                        un = hun.getText().toString(),
                        add = ha.getText().toString(),
                        city = hc.getText().toString(),
                        pass = hp.getText().toString(),
                        ph = hph.getText().toString(),
                        lon = hlon.getText().toString(),
                        lat = hlat.getText().toString();
                if (name.equals("") || add.equals("") || city.equals("") || email.equals("") ||
                        un.equals("") || pass.equals("") || ph.equals("") || lon.equals("")
                        || lat.equals("")){
                    Toast.makeText(HospitalRegistration.this,
                            "Fill all of the Above fields...",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                    ref.orderByChild("username").equalTo(un).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(HospitalRegistration.this, "Username Exists. Please use another username", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if (passLen(pass)) {
                                    DatabaseReference addData = database.getReference("Users");
                                    addData.child(un).child("name").setValue(name);
                                    addData.child(un).child("email").setValue(email);
                                    addData.child(un).child("username").setValue(un);
                                    addData.child(un).child("pass").setValue(pass);
                                    addData.child(un).child("phone").setValue(ph);
                                    addData.child(un).child("userType").setValue(type);
                                    addData.child(un).child("long").setValue(lon);
                                    addData.child(un).child("address").setValue(add);
                                    addData.child(un).child("city").setValue(city);
                                    addData.child(un).child("lat").setValue(lat);
                                    addData.child(un).child("status").setValue("Inactive");

                                    Intent n = new Intent(
                                            HospitalRegistration.this,
                                            LoginScreen.class
                                    );
                                    startActivity(n);

                                }
                                else  {
                                    Toast.makeText(HospitalRegistration.this, "Please Fill All of the Fields.", Toast.LENGTH_SHORT).show();
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