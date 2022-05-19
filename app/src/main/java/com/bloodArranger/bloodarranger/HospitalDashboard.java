package com.bloodArranger.bloodarranger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HospitalDashboard extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_dashboard);
        getSupportActionBar().hide();


        ImageView ap_add = findViewById(R.id.ap_add),
                ap_sub  = findViewById(R.id.ap_sub),
                bp_add  = findViewById(R.id.bp_add),
                bp_sub  = findViewById(R.id.bp_sub),
                an_add  = findViewById(R.id.an_add),
                an_sub  = findViewById(R.id.ann_sub),
                bn_add  = findViewById(R.id.bn_add),
                bn_sub  = findViewById(R.id.bn_sub),
                op_add  = findViewById(R.id.op_add),
                on_add = findViewById(R.id.on_add),
                on_sub = findViewById(R.id.on_sub),
                op_sub  = findViewById(R.id.op_sub),
                abp_add  = findViewById(R.id.abp_add),
                abp_sub  = findViewById(R.id.abp_sub),
                abn_add  = findViewById(R.id.abn_add),
                abn_sub  = findViewById(R.id.abn_sub);

        EditText ap  = findViewById(R.id.ap_et),
                an  = findViewById(R.id.an_et),
                bp  = findViewById(R.id.bp_et),
                bn  = findViewById(R.id.bn_et),
                op  = findViewById(R.id.op_et),
                on  = findViewById(R.id.on_et),
                abp  = findViewById(R.id.abp_et),
                abn  = findViewById(R.id.abn_et);

        Intent data = getIntent();
        String email = data.getStringExtra("email");

        DatabaseReference myRef = database.getReference("Users").child(email);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String n = dataSnapshot.child("name").getValue(String.class);
                name = n;
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });




        ImageView back = findViewById(R.id.back_HD);
        Button btnHead = findViewById(R.id.hospName_HD);
        btnHead.setText(email);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Stock");
        ref.orderByChild("username").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    DatabaseReference myRef = database.getReference("Stock").child(email);

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String e_ap = dataSnapshot.child("A+").getValue(String.class);
                            String e_an = dataSnapshot.child("A-").getValue(String.class);
                            String e_op = dataSnapshot.child("O+").getValue(String.class);
                            String e_on = dataSnapshot.child("O-").getValue(String.class);
                            String e_bp = dataSnapshot.child("B+").getValue(String.class);
                            String e_bn = dataSnapshot.child("B-").getValue(String.class);
                            String e_abp = dataSnapshot.child("AB+").getValue(String.class);
                            String e_abn = dataSnapshot.child("AB-").getValue(String.class);
                            ap.setText(e_ap);
                            an.setText(e_an);
                            bp.setText(e_bp);
                            bn.setText(e_bn);
                            op.setText(e_op);
                            on.setText(e_on);
                            abp.setText(e_abp);
                            abn.setText(e_abn);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });


                }
                else{
                    ap.setText("0");
                    an.setText("0");
                    bp.setText("0");
                    bn.setText("0");
                    op.setText("0");
                    on.setText("0");
                    abp.setText("0");
                    abn.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(
                        HospitalDashboard.this,
                        Home_Hospital.class
                );
                b.putExtra("email", email);
                startActivity(b);
            }
        });

        Button update = findViewById(R.id.UpdateRec);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e_ap = ap.getText().toString(),
                        e_an = an.getText().toString(),
                        e_bp = bp.getText().toString(),
                        e_bn = bn.getText().toString(),
                        e_op = op.getText().toString(),
                        e_on = on.getText().toString(),
                        e_abp = abp.getText().toString(),
                        e_abn = abn.getText().toString();
//
//                int e_ap = Integer.parseInt(ap.getText().toString()),
//                        e_an = Integer.parseInt(an.getText().toString()),
//                        e_bp = Integer.parseInt(bp.getText().toString()),
//                        e_bn = Integer.parseInt(bn.getText().toString()),
//                        e_op = Integer.parseInt(op.getText().toString()),
//                        e_on = Integer.parseInt(on.getText().toString()),
//                        e_abp = Integer.parseInt(abp.getText().toString()),
//                        e_abn = Integer.parseInt(abn.getText().toString());


                if (e_ap.equals("") || e_an.equals("") || e_bp.equals("") || e_bn.equals("") ||
                        e_on.equals("") || e_op.equals("") || e_abn.equals("") || e_abp.equals("")){
                    Toast.makeText(HospitalDashboard.this,
                            "Please make sure all fields are filled...",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    DatabaseReference myRef = database.getReference("Stock");
                    myRef.child(email).child("username").setValue(email);
                    myRef.child(email).child("name").setValue(name);
                    myRef.child(email).child("A+").setValue(e_ap);
                    myRef.child(email).child("A-").setValue(e_an);
                    myRef.child(email).child("B+").setValue(e_bp);
                    myRef.child(email).child("B-").setValue(e_bn);
                    myRef.child(email).child("O+").setValue(e_op);
                    myRef.child(email).child("O-").setValue(e_on);
                    myRef.child(email).child("AB+").setValue(e_abp);
                    myRef.child(email).child("AB-").setValue(e_abn);

                    Toast.makeText(HospitalDashboard.this, "Stock Updated.", Toast.LENGTH_SHORT).show();

                    Intent n = new Intent(HospitalDashboard.this, Home_Hospital.class);
                    n.putExtra("email",email);
                    startActivity(n);
                }
            }
        });


        ap_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = ap.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a + 1;
                    String aa = Integer.toString(a);
                    ap.setText(aa);
                }
            }
        });

        ap_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = ap.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a - 1;
                    String aa = Integer.toString(a);
                    ap.setText(aa);
                }
            }
        });


        an_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = an.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a + 1;
                    String aa = Integer.toString(a);
                    an.setText(aa);
                }
            }
        });

        an_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = an.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a - 1;
                    String aa = Integer.toString(a);
                    an.setText(aa);
                }
            }
        });


        bp_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = bp.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a + 1;
                    String aa = Integer.toString(a);
                    bp.setText(aa);
                }
            }
        });

        bp_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = bp.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a - 1;
                    String aa = Integer.toString(a);
                    bp.setText(aa);
                }
            }
        });



        bn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = bn.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a + 1;
                    String aa = Integer.toString(a);
                    bn.setText(aa);
                }
            }
        });

        bn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = bn.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a - 1;
                    String aa = Integer.toString(a);
                    bn.setText(aa);
                }
            }
        });


        op_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = op.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a + 1;
                    String aa = Integer.toString(a);
                    op.setText(aa);
                }
            }
        });


        op_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = op.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a - 1;
                    String aa = Integer.toString(a);
                    op.setText(aa);
                }
            }
        });


        on_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = on.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a + 1;
                    String aa = Integer.toString(a);
                    on.setText(aa);
                }
            }
        });


        on_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = on.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a - 1;
                    String aa = Integer.toString(a);
                    on.setText(aa);
                }
            }
        });


        abn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = abn.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a + 1;
                    String aa = Integer.toString(a);
                    abn.setText(aa);
                }
            }
        });

        abn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = abn.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a - 1;
                    String aa = Integer.toString(a);
                    abn.setText(aa);
                }
            }
        });



        abp_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = abp.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a + 1;
                    String aa = Integer.toString(a);
                    abp.setText(aa);
                }
            }
        });

        abp_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app = abp.getText().toString();
                if (app.equals("")){
                    Toast.makeText(HospitalDashboard.this, "Add some value in Edit Text", Toast.LENGTH_SHORT).show();
                } else {
                    int a = Integer.parseInt(app);
                    a = a - 1;
                    String aa = Integer.toString(a);
                    abp.setText(aa);
                }
            }
        });



    }
}