package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

//        button  = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openShop();
//            }
//        });

        Button shopUpdateButton = findViewById(R.id.shopUpdate);
        shopUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopUpdate = new Intent(MainActivity.this, ShopUpdate.class);
                startActivity(shopUpdate);
            }
        });

        button4=findViewById(R.id.signIn);
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
//    private void  openShop(){
//        Intent intent = new Intent(MainActivity.this, ShopDetails.class);
//        startActivity(intent);
//    }
}
