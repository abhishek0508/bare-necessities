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


public class ShopDetails extends AppCompatActivity {

    private static final String TAG = "shop";

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

