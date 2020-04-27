package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button button,button4,buttonShop;
    private static List<Shop> data;
    StorageReference storageRef;
    StorageReference imageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new DatabaseAdapter().getShops();

//        storageRef = FirebaseStorage.getInstance().getReference();
//        imageRef = storageRef.child(data.get(0).getImageUrl());
//        final String[] URL = new String[1];
//        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                URL[0] = uri.toString();
//            }
//        });

//        Log.d("URL Download",URL[0]);

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
