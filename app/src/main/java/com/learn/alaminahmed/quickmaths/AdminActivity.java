package com.learn.alaminahmed.quickmaths;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminActivity extends AppCompatActivity {

    private TextView message,pointLimit;
    private int point;
    private AdView banner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        banner1 = findViewById(R.id.banner1);
        MobileAds.initialize(this,getString(R.string.admob_app_id));
        //Banner ad
        AdRequest adRequest = new AdRequest.Builder().build();
        banner1.loadAd(adRequest);

        message = findViewById(R.id.adminMessage);
        pointLimit = findViewById(R.id.pointLimit);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Admin");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AdminModel admin = dataSnapshot.getValue(AdminModel.class);
                message.setText(admin.getMessage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference.child("todayPointLimit").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                point = dataSnapshot.getValue(Integer.class);
                pointLimit.setText(Integer.toString(point));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
