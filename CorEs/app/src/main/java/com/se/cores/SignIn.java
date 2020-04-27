package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignIn extends AppCompatActivity
{
    EditText emailId,password;
    private Button login_button;
    LoginBuilder loginBuilderObject;
    Login loginObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailId=(EditText) findViewById(R.id.emailId);
        password=(EditText) findViewById(R.id.password);
        login_button=(Button) findViewById(R.id.login);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = emailId.getText().toString().trim();
                String pswd = password.getText().toString().trim();
                if(email.isEmpty() || pswd.isEmpty())
                {
                    TextView view = (TextView) findViewById(R.id.errorMessage);
                    view.setVisibility(View.VISIBLE);
                }
                else
                {
                    loginBuilderObject = new LoginBuilder();
                    loginBuilderObject.setEmail(email);
                    loginBuilderObject.setPassword(pswd);

                    loginObject=loginBuilderObject.build();

                    DatabaseAdapter db = new DatabaseAdapter();
                    Boolean validate=db.validateCustomer(loginObject);

                    Log.d("validate","Bool value "+validate);
                    openHome();

                }

            }
        });

        String userType = "";
        SharedPreferences sp = getSharedPreferences("CoresPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("loggedIn", true);    // This should be inside an if condition that checks if email and password match
        editor.putString("userType", userType);     // Whether retailer or customer
        editor.apply();
    }
    //open home page
    public void openHome() {
        Intent home_intent = new Intent(this,Register.class);
        startActivity(home_intent);
    }

}
