package com.se.cores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

class DatabaseAdapter {
    DatabaseReference rootNodeReference;
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


    List<Shop> getShops(){
        List<Shop> shops = new ArrayList<Shop>();
        return shops;
    }

    void addNewRetailer(Retailer retailer) {

    }

    void addNewShop(Shop shop) {

        db.collection("shops").add(shop)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d("addNewShop", "Document added with ID: " + documentReference.getId());
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("addNewShop", "Error adding document", e);
                                                }
                                            });
        Log.d("addNewShop", "New shop added");
    }


}
