package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.firestore.GeoPoint;
import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN_ACTIVITY";
    private Button button,button4,buttonShop;
    private static List<Shop> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new DatabaseAdapter().getShops();
//        GeoPoint loc = findLocation();
//        Log.d(TAG, "here" + String.valueOf(loc));
//        DatabaseAdapter da = new DatabaseAdapter();
//        List<Shop> shops = da.getShops(loc);

        button  = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShop();
            }
        });

        Button retailerRegTestButton = findViewById(R.id.button3);
        retailerRegTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retReg = new Intent(MainActivity.this, RetailerRegistration.class);
                startActivity(retReg);
            }
        });

        button4=findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retReg = new Intent(MainActivity.this, RegisterSignIn.class);
                startActivity(retReg);
            }
        });

        buttonShop = findViewById(R.id.ShopList);
        buttonShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retReg = new Intent(MainActivity.this, ShopList.class);
                retReg.putExtra("LIST", (Serializable) data);
                startActivity(retReg);
            }
        });

    }
    private void  openShop(){
        Intent intent = new Intent(MainActivity.this, ShopDetails.class);
        startActivity(intent);
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
