package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import java.util.List;

public class ShopDetails extends AppCompatActivity implements FeedbackShopStatusDialog.FeedbackShopStatusListener,
                                                              FeedbackItemAvailableDialog.FeedbackItemDialogListener,
                                                              LoginForFeedbackDialog.LoginForFeedbackListener, OnMapReadyCallback {

    private static final String TAG = "SHOP DETAILS";
    private MapView mMapView;
    FeedBack feedBack;
    FeedbackBuilder feedbackBuilder;
    String shopID;
    private Shop shop;
    private TextView shopName,shopOpenCloseTime,itemAvailable,itemUnavailable;
    private Button mapButton;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private double latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        DatabaseAdapter db = new DatabaseAdapter();
        Intent intent = getIntent();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = findViewById(R.id.shopLocationMapView);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        shop = (Shop)intent.getSerializableExtra("SHOP");

        shopName = findViewById(R.id.shopNameTextView);
        shopOpenCloseTime = findViewById(R.id.shopTimingsTextView);
        shopName.setText(shop.getShopName());
        String textShopOpenCloseTime = "Timings: "+shop.getOpenTime()+" to "+shop.getCloseTime();
        shopOpenCloseTime.setText(textShopOpenCloseTime);
        itemAvailable = findViewById(R.id.itemsAvailableTextView);
        itemUnavailable = findViewById(R.id.itemsUnavailableTextView);
        latitude = shop.getLocationLat();
        longitude = shop.getLocationLong();
        Log.d("lat long", latitude + " " + longitude);
        GeoPoint loc = findLocation();
        Log.d(TAG, "here" + (loc));

        mapButton = findViewById(R.id.getDirectionsButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String current_lat = Double.toString(loc.getLatitude()), current_longi = Double.toString(loc.getLongitude()), destt_lat = Double.toString(shop.getLocationLat()), dest_longi = Double.toString(shop.getLocationLong());
                final Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?"
                                + "saddr="+ current_lat+","+current_longi + "&daddr="+ destt_lat+","+dest_longi  ));
                intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });


        Button feedbackButton = findViewById(R.id.feedbackButton);
        SharedPreferences sp = getSharedPreferences("CoresPref", MODE_PRIVATE);

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (sp.getBoolean("loggedIn", false) == true) {
                    feedbackBuilder = new FeedbackBuilder().setShopID("tj0AkwWcvgXH0sgwX7vQ");

                    DialogFragment itemFeedback = new FeedbackItemAvailableDialog();
                    itemFeedback.show(getSupportFragmentManager(), "itemFeedback");

                    DialogFragment statusFeedback = new FeedbackShopStatusDialog();
                    statusFeedback.show(getSupportFragmentManager(), "statusFeedback");

                    feedBack = feedbackBuilder.build();

//                    try {
//                        db.updateFeedback(feedBack);
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                else {
//                    DialogFragment notLoggedIn = new LoginForFeedbackDialog();
//                    notLoggedIn.show(getSupportFragmentManager(), "notLoggedInForFeedback");
//                }
            }
        });

        TextView itemFeedback = findViewById(R.id.feedbackItemsText);
        TextView statusFeedback = findViewById(R.id.feedbackStatusText);

        ArrayList<String> feedBackFields = new ArrayList<String>();
        feedBackFields.add("id");
        feedBackFields.add("8");
        feedBackFields.add("1");
        feedBackFields.add("10");
        feedBackFields.add("2");
        /*
        try {
            feedBackFields = db.readFeedback("tj0AkwWcvgXH0sgwX7vQ");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */
        int itemUpvotes, itemDownvotes, statusUpvotes, statusDownvotes;

        itemUpvotes = Integer.parseInt(feedBackFields.get(1));
        itemDownvotes = Integer.parseInt(feedBackFields.get(2));
        statusUpvotes = Integer.parseInt(feedBackFields.get(3));
        statusDownvotes = Integer.parseInt(feedBackFields.get(4));

        float itemTotal = itemUpvotes + itemDownvotes;
        float statusTotal = statusUpvotes + statusDownvotes;

        float itemReliablePercent = ((float)itemUpvotes/itemTotal) * 100.0F;
        float statusReliablePercent = ((float)statusUpvotes/statusTotal) * 100.0F;

        itemFeedback.setText(itemReliablePercent + " % people have found the item listing to be reliable");
        statusFeedback.setText(statusReliablePercent + " % people have found the open/closed status to be reliable");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Marker"));
        map.setMyLocationEnabled(true);
    }
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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

    @Override
    public void onLoginFeedbackDialogOKClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    public GeoPoint findLocation() {
        GeoPoint loc = null;
        Log.d("Find Location", "in find_location");
        String location_context = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) getSystemService(location_context);
        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.d(TAG, "enabled");
                return null;
            }
            locationManager.requestLocationUpdates(provider, 1000, 0,
                    new LocationListener() {

                        public void onLocationChanged(Location location) {
                        }

                        public void onProviderDisabled(String provider) {
                        }

                        public void onProviderEnabled(String provider) {
                        }

                        public void onStatusChanged(String provider, int status,
                                                    Bundle extras) {
                        }
                    });
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                loc = new GeoPoint(latitude, longitude);
                Log.d(TAG, String.valueOf(loc));
            }
        }
        return loc;
    }
}



