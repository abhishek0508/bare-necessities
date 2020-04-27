package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.firestore.GeoPoint;
import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN_ACTIVITY";
    private Button button,button4,buttonShop;
    private static List<Shop> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new DatabaseAdapter().getShops();

        button  = findViewById(R.id.button);
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

        button4=findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retReg = new Intent(MainActivity.this, RegisterSignIn.class);
                startActivity(retReg);
            }
        });

        buttonShop = findViewById(R.id.ShopList);
        buttonShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retReg = new Intent(MainActivity.this, ShopList.class);
                retReg.putExtra("LIST", (Serializable) data);
                startActivity(retReg);
            }
        });

    }
    private void  openShop(){
        Intent intent = new Intent(MainActivity.this, ShopDetails.class);
        startActivity(intent);
    }
}
