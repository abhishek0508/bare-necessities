package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;
//        The project consists of a ShopListActivity that displays the RecyclerView.
//        The CardView is added to the RecyclerView from the ShopAdapter class.
//        The DataModel is used to retrieve the data for
//        each CardView through getters. The Shop class
//        holds the arrays of textviews and drawables along with their ids.

public class ShopList extends AppCompatActivity {

    private static final String TAG = "Shop list";
    private static RecyclerView recyclerView;
    private static ShopAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<Shop> data;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        data = new ArrayList<>();
        for (int i = 0; i < ShopDataDummy.shopName.length; i++) {
            data.add(new ShopBuilder().setShopName(ShopDataDummy.shopName[i])
                            .setCloseTime(ShopDataDummy.closeTime[i])
                            .setOpenTime(ShopDataDummy.openTime[i])
                            .setImage_url(ShopDataDummy.image_url[i])
                            .build());
        }

//        GeoPoint loc = findLocation();
//        Log.d(TAG, "here" + String.valueOf(loc));
//        DatabaseAdapter da = new DatabaseAdapter();
//        List<Shop> shops = da.getShops(loc);

        adapter = new ShopAdapter(data);
        recyclerView.setAdapter(adapter);
        buildRecyclerView();
    }

    public void buildRecyclerView() {
        adapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(v.getContext(),Shop.class);
                intent.putExtra("ShopClass", ShopDataDummy.shopName);
                startActivity(intent);
            }
        });
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


