package com.se.cores;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.firebase.firestore.GeoPoint;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetailerRegistration extends AppCompatActivity implements OnMapReadyCallback {

    /**
     * This class defines UI code for the Retailer Registration screen of the app, in which a retailer can enter their personal details and their
     * shop's details. All of these details are added to the database, by creating objects of the Shop class and the Retailer class, and passing
     * these objects to functions addNewShop() and addNewRetailer(), which are defined in the DatabaseAdapter class.
     */

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final int PICK_IMAGE = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    ImageButton shopImage;
    MapView shopLocation;
    GoogleMap gMap;
    Bitmap uploadedShopImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_registration);

        shopImage = findViewById(R.id.imageRetailerReg);
        shopLocation = findViewById(R.id.mapShopLocationReg);
        final EditText retailerNameField = findViewById(R.id.editTextRetailerName);
        final EditText shopNameField = findViewById(R.id.editTextShopName);
        final EditText emailField = findViewById(R.id.emailRetailerReg);
        final EditText passwordField = findViewById(R.id.passwordRetailerReg);
        final EditText retailerPhoneField = findViewById(R.id.editTextRetailerPhone);
        final EditText GSTnumField = findViewById(R.id.editTextGSTNum);
        final Spinner shopTypeSelector = findViewById(R.id.spinnerShopType);
        final TimePicker shopOpenTimePicker = findViewById(R.id.shopOpenTime);
        final TimePicker shopCloseTimePicker = findViewById(R.id.shopCloseTime);
        final LatLng[] shopCoords = new LatLng[1];
        final Button saveButton = findViewById(R.id.saveButtonRR);


        // ----------------------------- Storing the user's choice for the shop type spinner --------------------------------------------
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                                                                R.array.shop_types,
                                                                                android.R.layout.simple_spinner_item
                                                                            );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shopTypeSelector.setAdapter(adapter);

        final String[] currentSelection = new String[1];

        shopTypeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                currentSelection[0] = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


        // -------------------------------------------- To let the user upload an image -------------------------------------------------

        requestMultiplePermissions();

        shopImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                pickIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });


        // ------------------------------To let the user choose their shop's location, using the Google Maps API --------------------------

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        shopLocation.onCreate(mapViewBundle);
        shopLocation.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                googleMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
                googleMap.setOnMyLocationClickListener(onMyLocationClickListener);
                enableMyLocationIfPermitted();

                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setMinZoomPreference(12);
            }

            private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
                    new GoogleMap.OnMyLocationButtonClickListener() {
                        @Override
                        public boolean onMyLocationButtonClick() {
                            gMap.setMinZoomPreference(15);
                            return false;
                        }
                    };

            private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
                    new GoogleMap.OnMyLocationClickListener() {
                        @Override
                        public void onMyLocationClick(@NonNull Location location) {

                            gMap.setMinZoomPreference(12);

                            shopCoords[0] = new LatLng(location.getLatitude(),
                                                       location.getLongitude());

                            CircleOptions circleOptions = new CircleOptions();
                            circleOptions.center(new LatLng(location.getLatitude(),
                                    location.getLongitude()));

                            circleOptions.radius(200);
                            circleOptions.fillColor(Color.BLUE);
                            circleOptions.strokeWidth(6);
                            gMap.addCircle(circleOptions);

                        }
                    };

        });

        if(gMap != null)
        {
            gMap.setOnPoiClickListener(pointOfInterest -> {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(pointOfInterest.latLng);
                gMap.addMarker(markerOptions);
                gMap.moveCamera(CameraUpdateFactory.newLatLng(pointOfInterest.latLng));
                shopCoords[0] = pointOfInterest.latLng;
            });
        }

        // -------------------------------- Work to be done when the save button is pressed ----------------------------------------------
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Extract data entered by user
                String retailerName = retailerNameField.getText().toString();
                String shopName = shopNameField.getText().toString();
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                String phoneNumber = retailerPhoneField.getText().toString();
                String GSTnum = GSTnumField.getText().toString();
                String shopType = currentSelection[0];
                String openingTime = shopOpenTimePicker.getHour() + ":" + shopOpenTimePicker.getMinute();
                String closingTime = shopCloseTimePicker.getHour() + ":" + shopCloseTimePicker.getMinute();
                GeoPoint loc = findLocation();
                double shopLatitude = loc.getLatitude();
                double shopLongitude = loc.getLongitude();
//                double shopLatitude = shopCoords[0].latitude;
//                double shopLongitude = shopCoords[0].longitude;

                // TODO: VALIDATE

                // TODO: ENCRYPT

                // Add all data to DB

                Map<String, Boolean> shopTypeMap = new HashMap<String, Boolean>();
                shopTypeMap.put(shopType, Boolean.TRUE);

                Shop newShop = new ShopBuilder().setShopName(shopName)
                                                .setGstNumber(GSTnum)
                                                .setShopType(shopTypeMap)
                                                .setOpenTime(openingTime)
                                                .setCloseTime(closingTime)
                                                .setLocationLat(shopLatitude)
                                                .setLocationLong(shopLongitude)
                                                .build();

                DatabaseAdapter db = new DatabaseAdapter();
                String shopID = db.addNewShop(newShop);

                Retailer newRetailer = new RetailerBuilder().setName(retailerName)
                                                            .setEmail(email)
                                                            .setPassword(password)
                                                            .setPhoneNumber(phoneNumber)
                                                            .setShopID(shopID)
                                                            .build();

//                db.addNewRetailer(newRetailer);

                // Go to app home screen

                Intent home = new Intent(RetailerRegistration.this, MainActivity.class);  // go to screen 4 + 6 (home, retailer logged in)
                startActivity(home);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == PICK_IMAGE) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    uploadedShopImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    shopImage.setImageBitmap(uploadedShopImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void requestMultiplePermissions() {
          Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        report.areAllPermissionsGranted();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                      //  Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        shopLocation.onSaveInstanceState(mapViewBundle);
    }

    private void enableMyLocationIfPermitted() {
        if ( ContextCompat.checkSelfPermission(RetailerRegistration.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RetailerRegistration.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (gMap != null) {
            gMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                }
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        shopLocation.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        shopLocation.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        shopLocation.onStop();
    }


    @Override
    public void onMapReady(GoogleMap map) {
        GeoPoint loc = findLocation();
        double shopLatitude = loc.getLatitude();
        double shopLongitude = loc.getLongitude();
        Log.d("displaying on the map", shopLatitude + " " +shopLongitude);
        map.addMarker(new MarkerOptions().position(new LatLng(shopLatitude, shopLongitude)).title("Marker"));
//        map.setMyLocationEnabled(true);
    }

    @Override
    protected void onPause() {
        shopLocation.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        shopLocation.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        shopLocation.onLowMemory();
    }

    public GeoPoint findLocation() {
        String TAG = "get current location";
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
