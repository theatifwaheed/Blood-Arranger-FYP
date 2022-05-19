package com.bloodArranger.bloodarranger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HospitalDetails extends AppCompatActivity {
    Button btn_hName,
            btn_Call;
    ImageView btn_back,
            locate;
//    TextView addressTV;

    String mapString, hPhone;
    String longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);
        getSupportActionBar().hide();

        requestPermissions();

//        addressTV = findViewById(R.id.addressTV);
        btn_back = findViewById(R.id.back_HD);
        locate = findViewById(R.id.location_H);
        btn_hName = findViewById(R.id.hospName_HD);
        btn_Call = findViewById(R.id.Call_HDet);

        TextView ap = findViewById(R.id.ap_tv),
                an = findViewById(R.id.an_tv),
                bp = findViewById(R.id.bp_tv),
                bn = findViewById(R.id.bn_tv),
                op = findViewById(R.id.op_tv),
                on = findViewById(R.id.on_tv),
                abp = findViewById(R.id.abp_tv),
                abn = findViewById(R.id.abn_tv);

        Intent getD = getIntent();
        String email = getD.getStringExtra("email"),
                h_username = getD.getStringExtra("username"),
                h_name = getD.getStringExtra("name"),
                activity = getD.getStringExtra("activity");


        btn_hName.setText(h_name);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Stock").child(h_username);
        DatabaseReference myRef2 = database.getReference("Users").child(h_username);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String AP = dataSnapshot.child("A+").getValue(String.class);
                String AN = dataSnapshot.child("A-").getValue(String.class);
                String BP = dataSnapshot.child("B+").getValue(String.class);
                String BN = dataSnapshot.child("B-").getValue(String.class);
                String OP = dataSnapshot.child("O+").getValue(String.class);
                String ON = dataSnapshot.child("O-").getValue(String.class);
                String ABP = dataSnapshot.child("AB+").getValue(String.class);
                String ABN = dataSnapshot.child("AB-").getValue(String.class);


                ap.setText(AP);
                an.setText(AN);
                bp.setText(BP);
                bn.setText(BN);
                op.setText(OP);
                on.setText(ON);
                abp.setText(ABP);
                abn.setText(ABN);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                longitude = dataSnapshot.child("long").getValue(String.class);
                latitude = dataSnapshot.child("lat").getValue(String.class);
                String phone = dataSnapshot.child("phone").getValue(String.class);
//                address = dataSnapshot.child("address").getValue(String.class);


                mapString = "https://www.google.com/maps/dir//" +
                        latitude + "%2F" + longitude + "/";
                hPhone  = phone;

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

//        addressTV.setText(address);

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mapString));
                startActivity(i);
            }
        });

        EditText callTv = findViewById(R.id.call_ET);
        callTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+ latitude + "," + longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        btn_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = "tel: " + hPhone;
                if (ActivityCompat.checkSelfPermission(HospitalDetails.this, Manifest.permission.CALL_PHONE) ==
                        PackageManager.PERMISSION_GRANTED)
                {

                    HospitalDetails.this.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(temp)));
                    finish();
                }
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse(temp));
//                startActivity(callIntent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.equals("Donor")){
                    Intent d = new Intent(
                            HospitalDetails.this,
                            Home_Donor.class
                    );
                    d.putExtra("email",email);
                    startActivity(d);
                }
                else if (activity.equals("Hospital")){
                    Intent h = new Intent(
                            HospitalDetails.this,
                            Home_Hospital.class
                    );
                    h.putExtra("email", email);
                    startActivity(h);
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