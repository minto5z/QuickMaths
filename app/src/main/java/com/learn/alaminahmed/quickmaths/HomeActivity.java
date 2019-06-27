package com.learn.alaminahmed.quickmaths;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private CardView play,profile,rewards,instruction,pointValue,admin,contact,exit,invite;
    private FirebaseUser user;

    private InterstitialAd mInterstitialAd;
    private AdView banner1;
    private int flag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = FirebaseAuth.getInstance().getCurrentUser();

        banner1 = findViewById(R.id.banner1);
        MobileAds.initialize(this,getString(R.string.admob_app_id));
        //Banner ad
        AdRequest adRequest = new AdRequest.Builder().build();
        banner1.loadAd(adRequest);


        play = findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isNetworkAvailable()){
                    Toast.makeText(HomeActivity.this,"Check your internet connection please !",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(HomeActivity.this,PlayActivity.class));
            }
        });

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 0;

                //Interstitial add
                mInterstitialAd = new InterstitialAd(getApplicationContext());
                mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

                //reload add
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        if(mInterstitialAd.isLoaded()&&flag == 0){
                            mInterstitialAd.show();
                        }
                    }

                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        flag = 1;
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });
                startActivity(new Intent(HomeActivity.this,ProfileActivity.class));

            }
        });

        rewards = findViewById(R.id.rewards);
        rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 0;

                //Interstitial add
                mInterstitialAd = new InterstitialAd(getApplicationContext());
                mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

                //reload add
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        if(mInterstitialAd.isLoaded()&&flag == 0){
                            mInterstitialAd.show();
                        }
                    }

                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        flag = 1;
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });
                startActivity(new Intent(HomeActivity.this,RewardsActivity.class));

            }
        });

        instruction = findViewById(R.id.instructionCardview);
        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;

                //Interstitial add
                mInterstitialAd = new InterstitialAd(getApplicationContext());
                mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

                //reload add
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        if(mInterstitialAd.isLoaded()&&flag == 0){
                            mInterstitialAd.show();
                        }
                    }

                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        flag = 1;
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });
                startActivity(new Intent(HomeActivity.this,InstructionActivity.class));

            }
        });

        pointValue = findViewById(R.id.poinValueCardview);
        pointValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;

                //Interstitial add
                mInterstitialAd = new InterstitialAd(getApplicationContext());
                mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

                //reload add
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        if(mInterstitialAd.isLoaded()&&flag == 0){
                            mInterstitialAd.show();
                        }
                    }

                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        flag = 1;
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });
                startActivity(new Intent(HomeActivity.this,PointValue.class));
            }
        });

        admin = findViewById(R.id.adminCardView);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 0;

                //Interstitial add
                mInterstitialAd = new InterstitialAd(getApplicationContext());
                mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

                //reload add
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        if(mInterstitialAd.isLoaded()&&flag == 0){
                            mInterstitialAd.show();
                        }
                    }

                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        flag = 1;
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());

                    }

                });
                startActivity(new Intent(HomeActivity.this,AdminActivity.class));

            }
        });

        contact = findViewById(R.id.contactCardview);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;

                //Interstitial add
                mInterstitialAd = new InterstitialAd(getApplicationContext());
                mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

                //reload add
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        if(mInterstitialAd.isLoaded()&&flag == 0){
                            mInterstitialAd.show();
                        }
                    }

                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        flag = 1;
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());

                    }

                });
                startActivity(new Intent(HomeActivity.this,ContactActivity.class));
            }
        });

        exit = findViewById(R.id.exitCardView);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                kill_activity();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        invite = findViewById(R.id.inviteCardView);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this,"Invite option will add in next update.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (user == null) {
            // User is signed in
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

    }

    void kill_activity()
    {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
