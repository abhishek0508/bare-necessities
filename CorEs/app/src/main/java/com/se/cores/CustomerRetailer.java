package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class CustomerRetailer extends AppCompatActivity {
    private Button button, button2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_retailer);

        button = findViewById(R.id.retail);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRetailerLogin();
            }
        });

        button2 = findViewById(R.id.customer);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomerLoginSignup();
            }
        });
    }
    public void openRetailerLogin() {
        Intent intent = new Intent(this,RetailerLogin.class);
        startActivity(intent);
    }

    public void openCustomerLoginSignup() {
        Intent intent = new Intent(this,CustomerLoginSignup.class);
        startActivity(intent);
    }
}