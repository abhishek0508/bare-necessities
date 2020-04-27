package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        String userType = "";
        SharedPreferences sp = getSharedPreferences("CoresPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("loggedIn", true);    // This should be inside an if condition that checks if email and password match
        editor.putString("userType", userType);     // Whether retailer or customer
        editor.apply();
    }
}
