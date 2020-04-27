package com.se.cores;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.koalap.geofirestore.GeoFire;
import com.koalap.geofirestore.GeoLocation;
import com.koalap.geofirestore.GeoQuery;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

class DatabaseAdapter {
    private static final String TAG = "Database Adapter";
    FirebaseFirestore db;
    CollectionReference shopsReference;
    CollectionReference customerReference;
    CollectionReference retailerReference;
    CollectionReference feedbackReference;

    DatabaseAdapter() {
        db = FirebaseFirestore.getInstance();
        shopsReference = db.collection("shops");
        customerReference = db.collection("customers");
        retailerReference = db.collection("retailers");
        feedbackReference = db.collection("feedback");
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

    String addNewShop(final Shop shop) {

        final GeoFire geoFire = new GeoFire(shopsReference);

        final String[] shopID = new String[1];

        shopsReference.add(shop)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("addNewShop", "Document added with ID: " + documentReference.getId());

                                shopID[0] = documentReference.getId();

                                geoFire.setLocation(documentReference.getId(),
                                                    new GeoLocation(shop.getLocationLat(), shop.getLocationLong()),
                                        null);

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
      
    public  List<Shop> getShops(){
        List<Shop> list = new ArrayList<>();
        shopsReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Shop shop = document.toObject(Shop.class);
                        Log.d(TAG, shop.toString());
                        list.add(shop);
                    }

                    Log.d(TAG, list.toString());
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        return list;
    }

    ArrayList<String> readFeedback(String shopID) throws ExecutionException, InterruptedException {

        Log.d("DATABASE ADAPTER", "TILL HERE");
        ArrayList<String> feedbackFields = new ArrayList<String>();

        Query feedbackQuery = feedbackReference.whereEqualTo("shopID", shopID);

        final QuerySnapshot querySnapshot = Tasks.await(feedbackQuery.get());
        List<DocumentSnapshot> feedDocs = querySnapshot.getDocuments();
        DocumentSnapshot feedbackDoc = feedDocs.get(0);

        Log.d("DATABASE ADAPTER", "I CAN REAAAAAAD!");
        Log.d(TAG, feedbackDoc.getId() + " => " + feedbackDoc.getData());

        feedbackFields.add(feedbackDoc.getId());
        feedbackFields.add(Objects.requireNonNull(feedbackDoc.get("itemAvailabilityYes")).toString());
        feedbackFields.add(Objects.requireNonNull(feedbackDoc.get("itemAvailabilityNo")).toString());
        feedbackFields.add(Objects.requireNonNull(feedbackDoc.get("trueStatusYes")).toString());
        feedbackFields.add(Objects.requireNonNull(feedbackDoc.get("trueStatusNo")).toString());


//        DocumentReference feedbackDoc = feedbackReference.document("ibFqY80y6R4RwM5vjQ3D ");
//        feedbackDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot snapshot,
//                                @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    Log.w(TAG, "Listen failed.", e);
//                    return;
//                }
//
//                if (snapshot != null && snapshot.exists()) {
//                    Log.d(TAG, "Current data: " + snapshot.getData());
//
//                    feedbackFields.add(snapshot.getId());
//                    feedbackFields.add(Objects.requireNonNull(snapshot.get("itemAvailabilityYes")).toString());
//                    feedbackFields.add(Objects.requireNonNull(snapshot.get("itemAvailabilityNo")).toString());
//                    feedbackFields.add(Objects.requireNonNull(snapshot.get("trueStatusYes")).toString());
//                    feedbackFields.add(Objects.requireNonNull(snapshot.get("trueStatusNo")).toString());
//
//                } else {
//                    Log.d(TAG, "Current data: null");
//                }
//            }
//        });

        return feedbackFields;
    }

    void writeNewFeedback(FeedBack feedBack) {
        int itemUpvotes = 0;
        int itemDownvotes = 0;
        int statusUpvotes = 0;
        int statusDownvotes = 0;

        if(feedBack.isItemAvailability()) {
            itemUpvotes = 1;
        }
        else {
            itemDownvotes = 1;
        }

        if(feedBack.isTrueStatus())
        {
            statusUpvotes = 1;
        }
        else {
            statusDownvotes = 1;
        }

        Map<String, Object> feedbackValues = new HashMap<String, Object>();
        feedbackValues.put("shopID", feedBack.getShopID());
        feedbackValues.put("itemAvailabilityYes", itemUpvotes);
        feedbackValues.put("itemAvailabilityNo", itemDownvotes);
        feedbackValues.put("trueStatusYes", statusUpvotes);
        feedbackValues.put("trueStatusNo", statusDownvotes);

        feedbackReference.add(feedbackValues)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("writeNewFeedback", "Document added with ID: " + documentReference.getId());

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("writeNewFeedback", "Error adding document", e);
                                }
                            });

    }

    void updateFeedback(FeedBack feedBack) throws ExecutionException, InterruptedException {
        // Read existing values

        Log.d("updateFeedback", "Reached here");

        // Get the feedback values from DB for a particular shop
        ArrayList<String> feedBackFields = readFeedback(feedBack.getShopID());

        if (feedBackFields.isEmpty()) {
            writeNewFeedback(feedBack);
            return;
        }

        int itemUpvotes, itemDownvotes, statusUpvotes, statusDownvotes;

        itemUpvotes = Integer.parseInt(feedBackFields.get(1));
        itemDownvotes = Integer.parseInt(feedBackFields.get(2));
        statusUpvotes = Integer.parseInt(feedBackFields.get(3));
        statusDownvotes = Integer.parseInt(feedBackFields.get(4));

        // Add +1 in 2 of the 4 values

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

        String feedbackDocID = feedBackFields.get(0);

        feedbackReference.document(feedbackDocID)
                        .update(
                                "itemAvailabilityYes", itemUpvotes,
                                "itemAvailabilityNo", itemDownvotes,
                                "trueStatusYes", statusUpvotes,
                                "trueStatusNo", statusDownvotes
                        )
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Feedback doc successfully updated!");
                        }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating feedback document", e);
                            }
                        });
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
    void updateShopDetails(String shopID, Shop shop){
        String TAG = "update shop details";
        shopsReference.document(shopID)
                .update(
                        "openCloseStatus", shop.isOpenCloseStatus(),
                        "openTime", shop.getOpenTime(),
                        "closeTime", shop.getCloseTime(),
                        "itemsAvailable", shop.getItemsAvailable(),
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
