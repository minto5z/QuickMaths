package com.learn.alaminahmed.quickmaths;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RewardsActivity extends AppCompatActivity {

    private TextView points,warning;
    private CardView requestCardView;
    private EditText phoneNo,amount;
    private String flag,useruid;
    private boolean checked;
    private int pointValue;
    private int validPoint;
    private AdView banner1;
    private int valid_method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        banner1 = findViewById(R.id.banner1);
        MobileAds.initialize(this,getString(R.string.admob_app_id));
        //Banner ad
        AdRequest adRequest = new AdRequest.Builder().build();
        banner1.loadAd(adRequest);

        points = findViewById(R.id.withdrawPoints);
        warning = findViewById(R.id.warning);

        phoneNo = findViewById(R.id.withdrawPhoneNo);
        amount = findViewById(R.id.withdrawAmount);

        //Fetch previous point

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        useruid = user.getUid();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(useruid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                points.setText(user.getPoints());
                String vPoint = user.getPoints();
                validPoint = Integer.parseInt(vPoint);
                if(validPoint < 3000){
                    warning.setText("** You have no enough points to withdraw **");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        requestCardView = findViewById(R.id.requestCardView);
        requestCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String withPhone,withAmount;
                withPhone = phoneNo.getText().toString().trim();
                withAmount = amount.getText().toString().trim();

                if(validPoint < 3000){
                    Toast.makeText(RewardsActivity.this,"**You don't have enough points to withdraw**",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(withPhone)||TextUtils.isEmpty(withAmount)){
                    Toast.makeText(RewardsActivity.this,"You have to fill up all required fields to complete this process !",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!checked){
                    Toast.makeText(RewardsActivity.this,"Transaction method is unchecked!",Toast.LENGTH_SHORT).show();
                    return;
                }

                //fetch point value from database (admin<<1_tk_for)
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Admin").child("1_tk_for");
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        pointValue = dataSnapshot.getValue(Integer.class);
                        //minus withdraw points from total points
                        int cutAmount = Integer.parseInt(withAmount);
                        valid_method = cutAmount;
                        String item = Integer.toString(validPoint-(pointValue*cutAmount));
                        if(Integer.parseInt(item)<0){
                            Toast.makeText(RewardsActivity.this,"You don't have enough money!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //Place withdraw request
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Withdraw_Request").child(flag).child(useruid);
                        ref.child(withPhone).setValue(withAmount).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                phoneNo.getText().clear();
                                amount.getText().clear();
                                checked = false;
                                Toast.makeText(RewardsActivity.this,"Your request is successfully placed to the database !",Toast.LENGTH_SHORT).show();

                            }
                        });
                        reference.child(useruid).child("points").setValue(item);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //Place withdraw request
               /* DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Withdraw_Request").child(flag).child(useruid);
                ref.child(withPhone).setValue(withAmount).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        phoneNo.getText().clear();
                        amount.getText().clear();
                        checked = false;
                        Toast.makeText(RewardsActivity.this,"Your request is successfully placed to the database !",Toast.LENGTH_SHORT).show();

                    }
                });*/
            }
        });

    }

    //Validate radio button
    public void checkButton(View view){
        checked = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.radioFlexiload:
                if(checked)
                    flag = "Flexiload";
                break;
            case R.id.radioBikash:
                if(checked) {
                    flag = "Bikash";
                }
        }
    }
}
