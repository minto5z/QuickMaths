package com.learn.alaminahmed.quickmaths;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class InstructionActivity extends AppCompatActivity {

    private TextView textView;
    private AdView banner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        banner1 = findViewById(R.id.banner1);
        MobileAds.initialize(this,getString(R.string.admob_app_id));
        //Banner ad
        AdRequest adRequest = new AdRequest.Builder().build();
        banner1.loadAd(adRequest);


        textView = findViewById(R.id.textView);
        String htmlText = "<h4>1.Don't use VPN.<h4>2.You have everyday's point limit.Check it from 'Admin Message' option.</h4>" +
                "<h4>3.You will get BKash payment when you will have 100tk or more,30tk or more you will get payment via Flexiload.</h4>" +
                "<h4>4.You will get your payment within 3 days of your request.</h4><h4>5.If you can reach month's point limit you will get 1000TK.That means 100tk bonus.</h4>" +
                "<h4>6.For more follow our fb group and keep eye on 'Admin Message'.</h4>" +
                "<h4>7.Work properly and be careful.</h4><br/><br/>";
        textView.setText(Html.fromHtml(htmlText));
    }
}