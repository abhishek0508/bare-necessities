package com.se.cores;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

class DatabaseAdapter {

    FirebaseFirestore db;

    DatabaseAdapter() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        rootNodeReference = database.getReference();
        db = FirebaseFirestore.getInstance();
    }

    void addNewCustomer(Customer customer) {
//                rootNodeReference.child("shops")
        Log.d("DatabaseAdapter", "New user added");

    }

    void addNewRetailer(Retailer retailer) {

    }

    void addNewShop(Shop shop) {


        Log.d("DatabaseAdapter", "New shop added");
    }


}
