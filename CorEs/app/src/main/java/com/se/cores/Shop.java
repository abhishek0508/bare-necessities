package com.se.cores;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseError;
import com.google.firebase.firestore.CollectionReference;
import com.koalap.geofirestore.GeoFire;
import com.koalap.geofirestore.GeoLocation;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public class Shop implements Serializable {

    private String shopName;
    private FeedBack feedBack;
    private String gstNumber;
    private List<String> itemUnavailable;
    private GeoFire geoFire;
    private Retailer retailerId;
    private Date openTime;
    private Date closeTime;
    private boolean openCloseStatus;
    private Map<String,Boolean> shopType;
    private String image_url;

    Shop(@NonNull ShopBuilder builder) {
        this.shopName = builder.shopName;
        this.feedBack = builder.feedBack;
        this.gstNumber = builder.gstNumber;
        this.itemUnavailable = builder.itemUnavailable;
        this.geoFire = builder.geoFire;
        this.retailerId = builder.retailerId;
        this.openTime = builder.openTime;
        this.closeTime = builder.closeTime;
        this.openCloseStatus = builder.openCloseStatus;
        this.shopType = builder.shopType;
    }

    public String getShopName() {
        return shopName;
    }

    public FeedBack getFeedBack() {
        return feedBack;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public List<String> getItemUnavailable() {
        return itemUnavailable;
    }

    public GeoFire getGeoFire() {
        return geoFire;
    }

    public Retailer getRetailerId() {
        return retailerId;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public boolean isOpenCloseStatus() {
        return openCloseStatus;
    }

    public Map<String, Boolean> getShopType() {
        return shopType;
    }

}

class ShopBuilder {

    String shopName;
    FeedBack feedBack;
    String gstNumber;
    List<String> itemUnavailable;
    GeoFire geoFire;
    Retailer retailerId;
    Date openTime;
    Date closeTime;
    boolean openCloseStatus;
    Map<String,Boolean> shopType;

    public ShopBuilder() {}

    public ShopBuilder setShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public ShopBuilder setFeedBack(FeedBack feedBack) {
        this.feedBack = feedBack;
        return this;
    }

    public ShopBuilder setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
        return this;
    }

    public ShopBuilder setItemUnavailable(List<String> itemUnavailable) {
        this.itemUnavailable = itemUnavailable;
        return this;
    }

    public ShopBuilder setGeoFire(CollectionReference ref, double latitude, double longitude) {
        geoFire = new GeoFire(ref);
        geoFire.setLocation("firebase-hq", new GeoLocation(latitude, -longitude),
                new GeoFire.CompletionListener() {
                    FirebaseError error;
                    @Override
                    public void onComplete(String key, Exception exception) {
                        if (error != null) {
                            System.err.println("There was an error saving the location to GeoFire: " + exception.toString());
                        } else {
                            System.out.println("Location saved on server successfully!");
                        }
                    }
                });

        return this;
    }

    public ShopBuilder setRetailerId(Retailer retailerId) {
        this.retailerId = retailerId;
        return this;
    }

    public ShopBuilder setOpenTime(Date openTime) {
        this.openTime = openTime;
        return this;
    }

    public ShopBuilder setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public ShopBuilder setOpenCloseStatus(boolean openCloseStatus) {
        this.openCloseStatus = openCloseStatus;
        return this;
    }

    public ShopBuilder setShopType(Map<String, Boolean> shopType) {
        this.shopType = shopType;
        return this;
    }

    public Shop build() {
        Shop shop = new Shop(this);
        validateUserObject(shop);
        return shop;
    }

    private void validateUserObject(Shop shop) {
        //Do some basic validations to check
        //if user object does not break any assumption of system
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Shop(String shopName, Date openTime, Date closeTime, String image_url) {
        this.shopName = shopName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.image_url = image_url;
    }
}
