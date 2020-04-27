package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register extends AppCompatActivity {
    private Button retailer_button,customer_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        retailer_button= findViewById(R.id.retailer);
        retailer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRetailerRegistration();
            }
        });

        customer_button= findViewById(R.id.customer);
        customer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomerRegistration();
            }
        });
    }

    public void openRetailerRegistration() {
        Intent register_intent = new Intent(this,RetailerRegistration.class);
        startActivity(register_intent);
    }
    public void openCustomerRegistration() {
        Intent register_intent = new Intent(this,CustomerRegistration.class);
        startActivity(register_intent);
    }

}
