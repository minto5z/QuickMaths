package com.learn.alaminahmed.quickmaths;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText regEmail,regPassword,regName,regPhoneNo;
    private CardView registerBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        regName = findViewById(R.id.fullName);
        regPhoneNo = findViewById(R.id.phoneNo);
        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.password);
        registerBtn = findViewById(R.id.cardViewRegister);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });
    }

    private void userRegister(){

        final String points = "0",todaysDate = "5/12/2018";
        final int todaysPoint = 0;
        final String name,email,phoneNo,pass;

        name = regName.getText().toString().trim();
        phoneNo = regPhoneNo.getText().toString().trim();
        email = regEmail.getText().toString().trim();
        pass = regPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(pass)||TextUtils.isEmpty(name)||TextUtils.isEmpty(phoneNo)){
            Toast.makeText(RegisterActivity.this,"You have to fill up all required fields to complete this process !",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering.....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {

                    User user = new User(
                            name,
                            email,
                            phoneNo,
                            points,
                            todaysDate,
                            todaysPoint
                    );

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

                    Toast.makeText(RegisterActivity.this, "Registration successful !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration error !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
