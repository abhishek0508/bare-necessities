package com.se.cores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.koalap.geofirestore.GeoFire;
import com.koalap.geofirestore.GeoLocation;

import java.util.ArrayList;
import java.util.List;

class DatabaseAdapter {
//    DatabaseReference rootNodeReference;
    FirebaseFirestore db;
    CollectionReference shopsReference;
    CollectionReference customerReference;
    CollectionReference retailerReference;

    DatabaseAdapter() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        rootNodeReference = database.getReference();
        db = FirebaseFirestore.getInstance();
        shopsReference = db.collection("shops");
        customerReference = db.collection("customers");
        retailerReference = db.collection("retailers");
    }

    void addNewCustomer(Customer customer) {
//                rootNodeReference.child("shops")
        Log.d("DatabaseAdapter", "New user added");
    }

    void addNewRetailer(Retailer retailer) {

    }

    void addNewShop(Shop shop) {

        GeoFire geoFire = new GeoFire(shopsReference);

        shopsReference.add(shop)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("addNewShop", "Document added with ID: " + documentReference.getId());

                                geoFire.setLocation(documentReference.getId(), new GeoLocation(shop.getLocationLat(), shop.getLocationLong()), null);
//                                        new GeoFire.CompletionListener() {
//                                            FirebaseError error;
//                                            @Override
//                                            public void onComplete(String key, Exception exception) {
//                                                if (error != null) {
//                                                    Log.w("addNewShop", "There was an error saving the location to GeoFire: " + exception.toString());
//                                                } else {
//                                                    Log.d("addNewShop", "Location saved successfully!");
//                                                }
//                                            }
//                                        });
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

    List<Shop> getShops(){
        List<Shop> shops = new ArrayList<Shop>();
        return shops;
    }
}
