package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
//        The project consists of a ShopListActivity that displays the RecyclerView.
//        The CardView is added to the RecyclerView from the ShopAdapter class.
//        The DataModel is used to retrieve the data for
//        each CardView through getters. The Shop class
//        holds the arrays of textviews and drawables along with their ids.


public class ShopList extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private static ShopAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<Shop> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        data = new ArrayList<Shop>();
        for (int i = 0; i < ShopDataDummy.shopName.length; i++) {
            data.add(new Shop(
                    ShopDataDummy.shopName[i],
                    ShopDataDummy.openTime[i],
                    ShopDataDummy.closeTime[i],
                    ShopDataDummy.image_url[i]
            ));
        }

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

}
