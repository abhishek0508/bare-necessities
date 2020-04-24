package com.se.cores;

import com.google.firebase.FirebaseError;
import com.google.firebase.firestore.CollectionReference;
import com.koalap.geofirestore.GeoFire;
import com.koalap.geofirestore.GeoLocation;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.Map;

//
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public FeedBack getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(FeedBack feedBack) {
        this.feedBack = feedBack;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public List<String> getItemUnavailable() {
        return itemUnavailable;
    }

    public void setItemUnavailable(List<String> itemUnavailable) {
        this.itemUnavailable = itemUnavailable;
    }

    public GeoFire getGeoFire() {
        return geoFire;
    }

    public void setGeoFire(CollectionReference ref, double latitude, double longitude) {
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
    }

    public Retailer getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Retailer retailerId) {
        this.retailerId = retailerId;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public boolean isOpenCloseStatus() {
        return openCloseStatus;
    }

    public void setOpenCloseStatus(boolean openCloseStatus) {
        this.openCloseStatus = openCloseStatus;
    }

    public Map<String, Boolean> getShopType() {
        return shopType;
    }

    public void setShopType(Map<String, Boolean> shopType) {
        this.shopType = shopType;
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
