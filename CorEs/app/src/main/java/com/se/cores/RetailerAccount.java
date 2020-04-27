//package com.example.se.cores;
package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

public class RetailerAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retailer_account);

        Button saveButton = findViewById(R.id.saveButtonRA);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add all data to DB

                // Go to app home screen

                Intent home = new Intent(RetailerAccount.this, RetailerAccount.class);  // go to screen 4 + 6 (home, retailer logged in)
                startActivity(home);
            }
        });
    }
}
