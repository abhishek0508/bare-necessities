package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.io.Serializable;
import java.util.List;

public class ShopList extends AppCompatActivity {

    private static final String TAG = "Shop list";
    private static RecyclerView recyclerView;
    private static ShopAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static List<Shop> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        data = (List<Shop>)intent.getSerializableExtra("LIST");

        recyclerView = findViewById(R.id.my_recycler_view);
        adapter = new ShopAdapter(data);
        adapter.notifyDataSetChanged();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        buildRecyclerView();

    }

    public void buildRecyclerView() {
        adapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(v.getContext(),ShopDetails.class);
                intent.putExtra("SHOP", (Serializable) data.get(position));
                startActivity(intent);
            }
        });

    }
}


