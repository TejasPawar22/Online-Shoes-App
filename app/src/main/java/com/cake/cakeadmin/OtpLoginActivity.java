package com.cake.cakeadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpLoginActivity extends AppCompatActivity {
    EditText editText1;
    Button sendotp;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);

        editText1=findViewById(R.id.input_mob_no);
        sendotp=findViewById(R.id.btnsend);
        progressBar=findViewById(R.id.probar1);
        auth = FirebaseAuth.getInstance();



        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!editText1.getText().toString().trim().isEmpty()){
                    if ((editText1.getText().toString().trim()).length()==10)
                    {

                        progressBar.setVisibility(View.VISIBLE);
                        sendotp.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + editText1.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                OtpLoginActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                        progressBar.setVisibility(View.GONE);
                                        sendotp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        progressBar.setVisibility(View.GONE);
                                        sendotp.setVisibility(View.VISIBLE);
                                        Toast.makeText(OtpLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                        progressBar.setVisibility(View.GONE);
                                        sendotp.setVisibility(View.VISIBLE);

                                        Intent intent=new Intent(getApplicationContext(),OutAuthActivity.class);
                                        intent.putExtra("mobile",editText1.getText().toString());
                                        intent.putExtra("backendotp",backendotp);
                                        startActivity(intent);

                                    }
                                }

                        );


                    }else {
                        Toast.makeText(OtpLoginActivity.this, "Please enter correct Number", Toast.LENGTH_SHORT).show();
                    }

                }else
                {
                    Toast.makeText(OtpLoginActivity.this, "Enter Mobile number", Toast.LENGTH_SHORT).show();
                }

            }
        });

        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(OtpLoginActivity.this,HomeActivity.class));
        }else{
            Toast.makeText(this, "please Login", Toast.LENGTH_SHORT).show();
        }

    }



}