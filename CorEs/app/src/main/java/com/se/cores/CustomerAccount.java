//package com.example.seproject;
package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

public class CustomerAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_account);

        Button saveButton = findViewById(R.id.saveButtonCA);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add all data to DB

                // Go to app home screen

                Intent home = new Intent(CustomerAccount.this, CustomerAccount.class);  // go to screen 4 + 5 (home, customer logged in)
                startActivity(home);
            }
        });
    }
}
