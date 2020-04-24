package com.se.cores;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

class DatabaseAdapter {
    DatabaseReference rootNodeReference;
    DatabaseAdapter() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        rootNodeReference = database.getReference();
    }

    void addNewUser(User user) {
//                rootNodeReference.child("shops")
        Log.d("DatabaseAdapter", "New user added");
    }


    List<Shop> getShops(){
        List<Shop> shops = new ArrayList<Shop>();
        return shops;
    }


}
