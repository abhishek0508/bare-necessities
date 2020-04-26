package com.se.cores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    CollectionReference feedbackReference;

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

    String addNewShop(final Shop shop) {

        final GeoFire geoFire = new GeoFire(shopsReference);

        final String[] shopID = new String[1];

        shopsReference.add(shop)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("addNewShop", "Document added with ID: " + documentReference.getId());

                                shopID[0] = documentReference.getId();

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

        return shopID[0];
    }

    List<Shop> getShops(){
        List<Shop> shops = new ArrayList<Shop>();
        return shops;
    }

    void updateFeedback(FeedBack feedBack, String shopID) {
        // Read existing values

        Log.d("updateFeedback", "Reached here");

        /*
        int itemUpvotes, itemDownvotes, statusUpvotes, statusDownvotes;

        DocumentReference feedbackDoc = feedbackReference.document(shopID);
        feedbackDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("updateFeedback", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("updateFeedback", "No such document");
                    }
                } else {
                    Log.d("updateFeedback", "get failed with ", task.getException());
                }
            }
        });



        // Add 1 in 2 of the 4 values

        if(feedBack.isItemAvailability()) {
            itemUpvotes += 1;
        }
        else {
            itemDownvotes += 1;
        }

        if(feedBack.isTrueStatus())
        {
            statusUpvotes += 1;
        }
        else {
            statusDownvotes += 1;
        }

        // Update in DB

         */
    }
}
