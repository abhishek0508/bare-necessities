package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button  = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShop();
            }
        });

        Button retailerRegTestButton = findViewById(R.id.button3);
        retailerRegTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retReg = new Intent(MainActivity.this, RetailerRegistration.class);
                startActivity(retReg);
            }
        });
    }
    private void  openShop(){
        Intent intent = new Intent(MainActivity.this, ShopDetails.class);
        startActivity(intent);
    }
}
