package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;
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
import androidx.fragment.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import org.w3c.dom.Text;
import android.view.View;
import android.widget.Button;
import java.util.List;


public class ShopDetails extends AppCompatActivity implements FeedbackShopStatusDialog.FeedbackShopStatusListener,
                                                              FeedbackItemAvailableDialog.FeedbackItemDialogListener,
                                                              LoginForFeedbackDialog.LoginForFeedbackListener {

    private static final String TAG = "shop";

    FeedBack feedBack;
    FeedbackBuilder feedbackBuilder;
    String shopID;
    private Shop shop;
    private TextView shopName,shopOpenCloseTime;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
//        DatabaseAdapter da = new DatabaseAdapter();
//        try {
//            da.getShopDetails("XG4yTz2TdiH8sNWNnouP");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        GeoPoint loc = findLocation();
//        Log.d(TAG, "here" + String.valueOf(loc));
//        List<Shop> shops = da.getShops(loc);
        DatabaseAdapter db = new DatabaseAdapter();

        Intent intent = getIntent();
        shop = (Shop)intent.getSerializableExtra("SHOP");

        shopName = findViewById(R.id.shopNameTextView);
        shopOpenCloseTime = findViewById(R.id.shopTimingsTextView);
        shopName.setText(shop.getShopName());
        String textShopOpenCloseTime = "Timings: "+shop.getOpenTime()+" to "+shop.getCloseTime();
        shopOpenCloseTime.setText(textShopOpenCloseTime);
          
        Button feedbackButton = findViewById(R.id.feedbackButton);

        SharedPreferences sp = getSharedPreferences("CoresPref", MODE_PRIVATE);

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sp.getBoolean("loggedIn", false) == true) {
                    feedbackBuilder = new FeedbackBuilder().setShopID("");

                    DialogFragment itemFeedback = new FeedbackItemAvailableDialog();
                    itemFeedback.show(getSupportFragmentManager(), "itemFeedback");

                    DialogFragment statusFeedback = new FeedbackShopStatusDialog();
                    statusFeedback.show(getSupportFragmentManager(), "statusFeedback");

                    feedBack = feedbackBuilder.build();

                    db.updateFeedback(feedBack);
                }
                else {
                    DialogFragment notLoggedIn = new LoginForFeedbackDialog();
                    notLoggedIn.show(getSupportFragmentManager(), "notLoggedInForFeedback");
                }
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
    @Override
    public void onLoginFeedbackDialogOKClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}

