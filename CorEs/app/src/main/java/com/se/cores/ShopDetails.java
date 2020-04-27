package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ShopDetails extends AppCompatActivity {
    private Shop shop;
    private TextView shopName,shopOpenCloseTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        Intent intent = getIntent();
        shop = (Shop)intent.getSerializableExtra("SHOP");

        shopName =(TextView) findViewById(R.id.shopName);
        shopOpenCloseTime = (TextView) findViewById(R.id.shopOpenTime);
        shopName.setText(shop.getShopName());
        String textShopOpenCloseTime = "Timings: "+shop.getOpenTime()+" to "+shop.getCloseTime();
        shopOpenCloseTime.setText(textShopOpenCloseTime);


    }
}
