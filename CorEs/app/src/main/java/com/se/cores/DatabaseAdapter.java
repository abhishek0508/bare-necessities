package com.se.cores;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.koalap.geofirestore.GeoFire;
import com.koalap.geofirestore.GeoLocation;
import com.koalap.geofirestore.GeoQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static androidx.core.content.ContextCompat.getSystemService;


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

    protected LocationManager locationManager;
    protected LocationListener locationListener;
//    void getCurrentLocation(){
//        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1000, this);
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        log.d("location", onLocationChanged();)
//    }

//    @Override
//    public void onLocationChanged(Location location) {
//        log.d("location", ("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude()));
//    }


    List<Shop> getShops(GeoPoint loc){

        List<Shop> shops = new ArrayList<Shop>();
        String TAG = "get shops";
        shopsReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Location locationA = new Location("point A");
                            locationA.setLatitude(loc.getLatitude());
                            locationA.setLongitude(loc.getLongitude());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                Log.d(TAG, document.getId() + " => " + doc.get("locationLat"));
                                Object latB = doc.get("locationLat");
                                Object lonB = doc.get("locationLong");
                                if(latB!=null && lonB !=null) {
                                    Location locationB = new Location("point B");
                                    locationB.setLatitude(new Double(latB.toString()));
                                    locationB.setLongitude(new Double(lonB.toString()));
                                    double distance = (locationA.distanceTo(locationB))/1000;
                                    Log.d(TAG, String.valueOf(distance));
                                    if(distance<1000) {
                                        Log.d(TAG, "inside " + String.valueOf(distance));
                                        shops.add(createShop(document));
                                    }
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        Log.d(TAG, shops.get(0).getShopName());
        return shops;
    }

    Shop createShop(DocumentSnapshot document) {
        ShopBuilder shopBuilder = new ShopBuilder();
        Map<String, Object> shopDoc = document.getData();
        shopBuilder.setShopName(shopDoc.get("shopName").toString());
        shopBuilder.setGstNumber(shopDoc.get("gstNumber").toString());
//                                shopBuilder.setItemAvailable((List<String>)shopDoc.get("itemAvailable"));
        shopBuilder.setItemUnavailable((List<String>)shopDoc.get("itemUnavailable"));
        shopBuilder.setLocationLat(new Double(shopDoc.get("locationLat").toString()));
        shopBuilder.setLocationLong(new Double(shopDoc.get("locationLong").toString()));
//                                shopBuilder.setRetailerId((shopDoc.get("retailerId").toString()));
        shopBuilder.setOpenTime(shopDoc.get("openTime").toString());
        shopBuilder.setCloseTime(shopDoc.get("closeTime").toString());
        shopBuilder.setOpenCloseStatus(new Boolean(shopDoc.get("openCloseStatus").toString()));
        Shop shop = new Shop(shopBuilder);
        return shop;
    }

    private interface MyCallback {
        void onCallback(Shop shop);
//        void onCallback(List<Shop> shops);
    }

    void getShopDetails(String shopID) {
        Shop shop;
        getShopDetails(shopID, new MyCallback()  {
            @Override
            public void onCallback(Shop shop1) {
                //Do what you need to do with your list


                Log.d("get shop details", "DocumentSnapshot data: " + shop1.getShopName());
            }
        });
    }



    void getShopDetails(String shopID, MyCallback myCallback){
        String TAG = "get shop details";
        shopsReference.document(shopID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                Shop shop = createShop(document);
                                Log.d(TAG, "DocumentSnapshot data: " + shop);
                                myCallback.onCallback(shop);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }


//    async Shop  getShopDetails(String shopID) {
//        var cityRef;
//        try{
//            cityRef = await db.collection('cities').doc('SF');
//            // do stuff
//        } catch(e) {
//            // error!
//        }
//

//    @WorkerThread
//    void  getShopDetails(String shopID) throws ExecutionException, InterruptedException {
//        String TAG = "get shop details";
//        ShopBuilder shopBuilder = new ShopBuilder();
//        final Shop[] shop = new Shop[1];
//        DocumentSnapshot document = Tasks.await(shopsReference.document(shopID).get());
//        if (document.exists()) {
//            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//            shop[0] = createShop(document);
//            Log.d(TAG, "DocumentSnapshot data: " + shop[0]);
//        } else {
//            Log.d(TAG, "No such document");
//        }
////                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
////                    @Override
////                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
////                        if (task.isSuccessful()) {
////                            DocumentSnapshot document = task.getResult();
////                            if (document.exists()) {
////                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
////                                shop[0] = createShop(document);
////                                Log.d(TAG, "DocumentSnapshot data: " + shop[0]);
////                            } else {
////                                Log.d(TAG, "No such document");
////                            }
////                        } else {
////                            Log.d(TAG, "get failed with ", task.getException());
////                        }
////                    }
////                }));
////        Log.d(TAG, shop[0].getShopName());
////        return shop[0];
//    }
//
//    Shop  getShopDetails(String shopID) throws InterruptedException {
//        String TAG = "get shop details";
//        ShopBuilder shopBuilder = new ShopBuilder();
//        final Shop[] shop = new Shop[1];
//        shopsReference.document(shopID)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                                shop[0] = createShop(document);
//                                Log.d(TAG, "DocumentSnapshot data: " + shop[0]);
//                            } else {
//                                Log.d(TAG, "No such document");
//                            }
//                        } else {
//                            Log.d(TAG, "get failed with ", task.getException());
//                        }
//                    }
//                });
//        int i = 1000000000;
//        while(shop[0]==null) {
////            Thread.sleep(5 * 1000);
//            i--;
//            if(i==0)
//                break;
//        }
////        Log.d(TAG, shop[0].getShopName());
//        return shop[0];
//    }

    // not verified
    void updateShopDetails(String shopID, Shop shop){
        String TAG = "update shop details";
        shopsReference.document(shopID)
                .update(
                        "openTime", shop.getOpenTime(),
                        "closeTime", shop.getCloseTime(),
//                        "itemAvailable", shop.getItemAvailable(),
                        "itemUnavailable", shop.getItemUnavailable()
                        )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapsh successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });

    }
}
