package com.se.cores;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.CountryCodePicker;

import java.util.concurrent.TimeUnit;


public class CustomerOtp extends AppCompatActivity {
    private Button submit;
    private static final String TAG = "CustomerOTPActivity";
    CountryCodePicker ccp;
    EditText phoneNumber;
    String codeSent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_otp);
        submit = (Button) findViewById(R.id.generateOTP);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             sendVerificationCode();
             openMainPage();
            }
        });
    }

    private void sendVerificationCode(){
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        String phone = "+"+ccp.getSelectedCountryCode()+phoneNumber;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }
    };


    public void openMainPage(){
        Intent intent = new Intent(this,EnterOtp.class);
        startActivity(intent);
    }

}