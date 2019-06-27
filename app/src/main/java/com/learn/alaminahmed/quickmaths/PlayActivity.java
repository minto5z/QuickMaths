package com.learn.alaminahmed.quickmaths;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    private TextView t1,t2,t3,t4,t5,finishBtn,clickAd;
    private EditText editText;
    private Button submitBtn;
    private String[] op = {"+","x"};
    private int point = 0,i = 0,second = 10;
    int flag = 0,flag1 = 0;
    private int temp;
    private int pointLimit;
    private int count = 1 ;
    private String databaseDate;
    private int todaysPoint;
    private int point1,limit;
    private int click_validate = 0;

    Random random1 = new Random();
    Random random2 = new Random();
    Random random3 = new Random();

    int num1 = random1.nextInt(20);
    int num2 = random2.nextInt(9);
    int sign = random3.nextInt(op.length);

    private AdView banner1;
    private InterstitialAd mInterstitialAd;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PlayActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //this method is for controlling user play time
        controlActivity();

        finishBtn = findViewById(R.id.finishButton);
        finishBtn.setEnabled(false);
        finishBtn.setText("");

        t1 = findViewById(R.id.num1);
        t1.setText(String.valueOf(num1));

        t2 = findViewById(R.id.num2);
        t2.setText(String.valueOf(num2));

        clickAd = findViewById(R.id.clickAd);

        t3 = findViewById(R.id.operator);
        t3.setText(String.valueOf(op[sign]));

        t4 = findViewById(R.id.point_count);

        submitBtn = findViewById(R.id.submitBtn);

        banner1 = findViewById(R.id.banner1);
        MobileAds.initialize(this,getString(R.string.admob_app_id));
        //Banner ad
        AdRequest adRequest = new AdRequest.Builder().build();
        banner1.loadAd(adRequest);
        //Interstitial add
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        //reload add
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        submitBtn.setEnabled(false);
        //Button timer when enter into play activity
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                submitBtn.setText(new SimpleDateFormat("mm:ss").format(new Date(millisUntilFinished)));
            }

            public void onFinish() {
                submitBtn.setText("Submit");
                submitBtn.setEnabled(true);
            }
        }.start();


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLeftApplication() {
                new CountDownTimer(10000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        Toast.makeText(PlayActivity.this,"Scroll up and down and wait "+new SimpleDateFormat("ss").format(new Date(millisUntilFinished))+"s.", Toast.LENGTH_LONG).show();
                    }

                    public void onFinish() {
                        click_validate = 1;
                        Toast.makeText(PlayActivity.this,"Your task is successful! Go back and touch on 'FINISH' button.",Toast.LENGTH_SHORT).show();
                    }
                }.start();
            }
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adsFlag = 0;
                //check whether net is connected or not
                if(!isNetworkAvailable()){
                    Toast.makeText(PlayActivity.this,"Check your internet connection please !",Toast.LENGTH_SHORT).show();
                    return;
                }
                controlActivity();
                if((count%15) == 0){
                    adsFlag = 1;
                    //Alert dialog for click ad
                    AlertDialog alertDialog = new AlertDialog.Builder(PlayActivity.this).create();
                    alertDialog.setTitle("Alert!");
                    alertDialog.setMessage("Complete your task.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    mInterstitialAd.show();
                                }
                            });
                    alertDialog.show();

                    new CountDownTimer(30000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            submitBtn.setEnabled(false);
                        }
                        public void onFinish() {
                            submitBtn.setEnabled(true);
                            if(click_validate == 1) {
                                finishBtn.setEnabled(true);
                                finishBtn.setText("FINISH");
                                click_validate = 0;
                            }
                            else {
                                clickAd.setText("You didn't complete your task.Your point is 0.");
                                point = 0;
                                t4.setText(Integer.toString(point));
                            }
                        }
                    }.start();
                }
                else{
                    clickAd.setText("");
                    finishBtn.setEnabled(false);
                    finishBtn.setText("");
                }
                //show ad
                if(mInterstitialAd.isLoaded() && adsFlag == 0){
                    mInterstitialAd.show();
                }
                ////////////////////
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String useruid = user.getUid();

                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users").child(useruid).child("todayPoint");
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        point1 = dataSnapshot.getValue(Integer.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("Admin").child("todayPointLimit");
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        limit = dataSnapshot.getValue(Integer.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                /////////////////////
                submitBtn.setEnabled(false);
                //Button timer
                new CountDownTimer(10000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        submitBtn.setText(new SimpleDateFormat("mm:ss").format(new Date(millisUntilFinished)));
                    }

                    public void onFinish() {

                        submitBtn.setText("Submit");
                        if((point1+10) < limit) {
                            submitBtn.setEnabled(true);
                        }
                        else{
                            clickAd.setText("**You are out of limit of today's point**");
                            submitBtn.setEnabled(false);
                        }
                    }
                }.start();
                perform_action();
                count += 1;
            }
        });

    }
    public void perform_action(){

        String txt;
        editText = findViewById(R.id.answer);
        txt = editText.getText().toString();
        if(TextUtils.isEmpty(txt)){
            Toast.makeText(PlayActivity.this,"Enter your answer !",Toast.LENGTH_SHORT).show();
            return;
        }

        String text = editText.getText().toString();

        int userData = Integer.parseInt(text);
        t5 = findViewById(R.id.flag);

        if(op[sign] == "+" && userData==(num1+num2)){
            point +=10;
            t5.setText(" Correct");
        }
        else if(op[sign] == "x" && userData==(num1*num2)){
            point+=10;
            t5.setText(" Correct");
        }
        else{
            point-=5;
            t5.setText(" Wrong");
        }

        t4.setText(String.valueOf(point));

        num1 = random1.nextInt(10);
        num2 = random2.nextInt(5);
        sign = random3.nextInt(op.length);

        t1.setText(String.valueOf(num1));
        t2.setText(String.valueOf(num2));
        t3.setText(String.valueOf(op[sign]));

        editText.getText().clear();

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int newPoints = point;

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String useruid = user.getUid();
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(useruid);

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        String st1 = user.getPoints();
                        int oldPoints = Integer.parseInt(st1);

                        String data = Integer.toString(newPoints+oldPoints);
                        ref.child("points").setValue(data);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //Pass points to CongratulationActivity
                String str = Integer.toString(point);
                Bundle b = new Bundle();
                Intent i = new Intent(PlayActivity.this, CongratulationActivity.class);
                b.putString("key",str);
                i.putExtras(b);
                startActivity(i);

                //kill play activity
                kill_activity();
            }


        });

    }

    public void controlActivity() {

        final String currentDate;

        final int temp1;

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        currentDate = df.format(c);

        //Invoke todays point limit from admin
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Admin").child("todayPointLimit");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pointLimit = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String useruid = user.getUid();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(useruid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                databaseDate = user.getTodayDate();

                int todaysPointTemp = 0;
                if(flag1 ==0) {
                    todaysPoint = user.getTodayPoint();
                    flag1 = 1;
                }
                todaysPointTemp = todaysPoint;
                todaysPointTemp +=point;
                if (currentDate.equals(databaseDate)) {
                    if (todaysPoint < pointLimit)
                    {
                        temp = 0;
                        reference.child(useruid).child("todayPoint").setValue(todaysPointTemp);
                    }
                    else
                    {
                        temp = 1;
                        submitBtn.setEnabled(false);
                        clickAd.setText("**You are out of limit of today's point**");
                    }
                }
                else
                {
                    todaysPoint = 0;
                    reference.child(useruid).child("todayPoint").setValue(0);
                    reference.child(useruid).child("todayDate").setValue(currentDate);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void kill_activity()
    {
        finish();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

