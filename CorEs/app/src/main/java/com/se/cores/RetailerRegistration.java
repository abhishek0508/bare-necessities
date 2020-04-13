package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

public class RetailerRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_registration);

        final ImageView shopImage = findViewById(R.id.imageViewShop);
        final EditText retailerNameField = findViewById(R.id.editTextRetailerName);
        final EditText shopNameField = findViewById(R.id.editTextShopName);
        final EditText retailerPhoneField = findViewById(R.id.editTextRetailerPhone);
        final EditText GSTnumField = findViewById(R.id.editTextGSTNum);
        final Spinner shopTypeSelector = findViewById(R.id.spinnerShopType);
        final TimePicker shopOpenTimePicker = findViewById(R.id.shopOpenTime);
        final TimePicker shopCloseTimePicker = findViewById(R.id.shopCloseTime);
        // mapview
        final Button saveButton = findViewById(R.id.saveButtonRR);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                                                                R.array.shop_types,
                                                                                android.R.layout.simple_spinner_item
                                                                            );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shopTypeSelector.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Extract data entered by user

                String retailerName = retailerNameField.getText().toString();
                String shopName = shopNameField.getText().toString();
                String phoneNumber = retailerPhoneField.getText().toString();
                String GSTnum = GSTnumField.getText().toString();
                String shopType = ;
                String openingTime = shopOpenTimePicker.getHour() + ":" + shopOpenTimePicker.getMinute();
                String closingTime = shopCloseTimePicker.getHour() + ":" + shopCloseTimePicker.getMinute();

                // VALIDATE!


                // Add all data to DB

                // Go to app home screen

                Intent home = new Intent(RetailerRegistration.this, );  // go to screen 4 + 6 (home, retailer logged in)
                startActivity(home);
            }
        });
    }
}
