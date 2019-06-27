package com.learn.alaminahmed.quickmaths;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class ContactActivity extends AppCompatActivity {

    private CardView cardView;
    private AdView banner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        banner1 = findViewById(R.id.banner1);
        MobileAds.initialize(this,getString(R.string.admob_app_id));
        //Banner ad
        AdRequest adRequest = new AdRequest.Builder().build();
        banner1.loadAd(adRequest);

        cardView = findViewById(R.id.cardViewContact);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://m.facebook.com/groups/1969931646635212?view=admin_activities&notif_t=group_name_change&notif_id=1545576200029645&ref=m_notif";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
