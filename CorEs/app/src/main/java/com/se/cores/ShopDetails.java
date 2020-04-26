package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class ShopDetails extends AppCompatActivity {
    private Shop shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        Intent intent = getIntent();
        shop = (Shop)intent.getSerializableExtra("SHOP");
    }
}
