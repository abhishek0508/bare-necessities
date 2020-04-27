package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;
import java.util.concurrent.ExecutionException;
=======
import androidx.fragment.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.List;
>>>>>>> 7c22e3a4733fc52c458517cea62b0d280d16a756


public class ShopDetails extends AppCompatActivity implements FeedbackShopStatusDialog.FeedbackShopStatusListener,
                                                              FeedbackItemAvailableDialog.FeedbackItemDialogListener {

<<<<<<< HEAD
    private static final String TAG = "shop";

=======
    FeedBack feedBack;
    FeedbackBuilder feedbackBuilder;
    String shopID;
    private Shop shop;
    
>>>>>>> 7c22e3a4733fc52c458517cea62b0d280d16a756
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
<<<<<<< HEAD
//        DatabaseAdapter da = new DatabaseAdapter();
//        try {
//            da.getShopDetails("XG4yTz2TdiH8sNWNnouP");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        GeoPoint loc = findLocation();
//        Log.d(TAG, "here" + String.valueOf(loc));
//        List<Shop> shops = da.getShops(loc);
=======
        DatabaseAdapter db = new DatabaseAdapter();

        Intent intent = getIntent();
        shop = (Shop)intent.getSerializableExtra("SHOP");

        Button feedbackButton = findViewById(R.id.feedbackButton);

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                feedbackBuilder = new FeedbackBuilder();

                DialogFragment itemFeedback = new FeedbackItemAvailableDialog();
                itemFeedback.show(getSupportFragmentManager(), "itemFeedback");

                DialogFragment statusFeedback = new FeedbackShopStatusDialog();
                statusFeedback.show(getSupportFragmentManager(), "statusFeedback");

                feedBack = feedbackBuilder.build();

                db.updateFeedback(feedBack, shopID);
            }
        });
    }

    @Override
    public void onItemDialogPositiveClick(DialogFragment dialog) {
        feedbackBuilder.setItemAvailability(true);
    }

    @Override
    public void onItemDialogNegativeClick(DialogFragment dialog) {
        feedbackBuilder.setItemAvailability(false);
    }

    @Override
    public void onStatusDialogPositiveClick(DialogFragment dialog) {
        feedbackBuilder.setTrueStatus(true);
    }

    @Override
    public void onStatusDialogNegativeClick(DialogFragment dialog) {
        feedbackBuilder.setTrueStatus(false);
>>>>>>> 7c22e3a4733fc52c458517cea62b0d280d16a756
    }

//    public GeoPoint findLocation() {
//        GeoPoint loc = null;
//        Log.d("Find Location", "in find_location");
//        String location_context = Context.LOCATION_SERVICE;
//        LocationManager locationManager = (LocationManager) getSystemService(location_context);
//        List<String> providers = locationManager.getProviders(true);
//        for (String provider : providers) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                Log.d(TAG, "enabled");
//                return null;
//            }
//            locationManager.requestLocationUpdates(provider, 1000, 0,
//                    new LocationListener() {
//
//                        public void onLocationChanged(Location location) {
//                        }
//
//                        public void onProviderDisabled(String provider) {
//                        }
//
//                        public void onProviderEnabled(String provider) {
//                        }
//
//                        public void onStatusChanged(String provider, int status,
//                                                    Bundle extras) {
//                        }
//                    });
//            Location location = locationManager.getLastKnownLocation(provider);
//            if (location != null) {
//                double latitude = location.getLatitude();
//                double longitude = location.getLongitude();
//                loc = new GeoPoint(latitude, longitude);
//                Log.d(TAG, String.valueOf(loc));
//            }
//        }
//        return loc;
//    }
}

