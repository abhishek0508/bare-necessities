package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterSignIn extends AppCompatActivity {
    private Button signIn_button,register_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sign_in);

        signIn_button=(Button) findViewById(R.id.signIn);
        signIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignIn();
            }
        });

        register_button=(Button) findViewById(R.id.register);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistration();
            }
        });
    }
    public void openSignIn() {
        Intent signIn_intent = new Intent(this,SignIn.class);
        startActivity(signIn_intent);
    }

    public void openRegistration() {
        Intent register_intent = new Intent(this,Register.class);
        startActivity(register_intent);
    }
}
