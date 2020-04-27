package com.se.cores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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

    void addNewCustomer(Customer customer)
    {
        Log.d("DatabaseAdapter", "New user added");
        customerReference.add(customer)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("addNewUser", "User added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("addNewUser", "Error adding USER", e);

                            }
                        });
    }

    Boolean validateCustomer(Login login)
    {
        Log.d("DatabaseAdapter", "validate data");
        LoginBuilder loginBuilderObject = new LoginBuilder();
        loginBuilderObject.setStatus(false);
        Boolean validate=false;
        customerReference.whereEqualTo("email",login.getEmail()).whereEqualTo("password",login.getPassword())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            loginBuilderObject.setStatus(true);
                            Login loginObject;
                            loginObject=loginBuilderObject.build();
                            Log.d("check",loginObject.getStatus().toString());
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d("readUser", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("readUser", "Error getting documents: ", task.getException());
                        }
                    }
                });


        return validate;
    }


    void addNewRetailer(Retailer retailer) {

    }

    void addNewShop(final Shop shop) {

        final GeoFire geoFire = new GeoFire(shopsReference);

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
