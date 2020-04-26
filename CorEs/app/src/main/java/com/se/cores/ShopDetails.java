package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class ShopDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        DatabaseAdapter da = new DatabaseAdapter();
//        da.getShopDetails("XG4yTz2TdiH8sNWNnouP");
    }
}

