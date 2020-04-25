package com.se.cores;

import com.google.common.collect.ArrayTable;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopDataDummy {

//  fetch data from firebase
//   create array Arralist of Shops
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<Shop> shopList = new ArrayList<>();

    static String[] shopName = {"Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich","JellyBean", "Kitkat", "Lollipop", "Marshmallow"};
    static String[] image_url = {"adfdfd","adfdfd","adfdfd","adfdfd"};
    static Date[] openTime = {};
    static Date[] closeTime = {};


}
