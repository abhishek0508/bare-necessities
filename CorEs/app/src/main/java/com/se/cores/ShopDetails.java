package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import androidx.fragment.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

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
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

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
        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

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
}

