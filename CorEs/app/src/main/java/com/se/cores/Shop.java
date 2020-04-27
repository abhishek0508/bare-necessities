package com.se.cores;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Shop implements Serializable{

    private String shopName;
    private String gstNumber;
    private List<String> itemUnavailable;
    private List<String> itemAvailable;
    private double locationLat;
    private double locationLong;
    private String retailerId;
    private String openTime;
    private String closeTime;
    private boolean openCloseStatus;
    private Map<String,Boolean> shopType;
    private String imageUrl;

    // no arg constructor to deal with firebase
    public Shop(){}

    Shop(@NonNull ShopBuilder builder) {
        this.shopName = builder.shopName;
        this.gstNumber = builder.gstNumber;
        this.itemUnavailable = builder.itemUnavailable;
        this.itemAvailable = builder.itemAvailable;
        this.locationLat = builder.locationLat;
        this.locationLong = builder.locationLong;
        this.retailerId = builder.retailerId;
        this.openTime = builder.openTime;
        this.closeTime = builder.closeTime;
        this.openCloseStatus = builder.openCloseStatus;
        this.shopType = builder.shopType;
        this.imageUrl = builder.imageUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public List<String> getItemUnavailable() {
        return itemUnavailable;
    }

    public List<String> getItemsAvailable() {
        return itemAvailable;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public double getLocationLong() {
        return locationLong;
    }

    public String getRetailerId() {
        return retailerId;
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public boolean isOpenCloseStatus() {
        return openCloseStatus;
    }

    public Map<String, Boolean> getShopType() {
        return shopType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopName='" + shopName + '\'' +
                ", openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", image_url='" + imageUrl + '\'' +
                '}';
    }
}

class ShopBuilder {

    String shopName;
    String gstNumber;
    List<String> itemUnavailable;
    List<String> itemAvailable;
    double locationLat;
    double locationLong;
    String retailerId;
    String openTime;
    String closeTime;
    boolean openCloseStatus;
    Map<String,Boolean> shopType;
    String imageUrl;

    public ShopBuilder() {}

    public ShopBuilder setShopName(String shopName) {
        this.shopName = shopName;
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

    public ShopBuilder setItemsAvailable(List<String> itemsAvailable) {
        this.itemAvailable = itemsAvailable;
        return this;
    }

    public ShopBuilder setLocationLat(double locationLat) {
        this.locationLat = locationLat;
        return this;
    }

    public ShopBuilder setLocationLong(double locationLong) {
        this.locationLong = locationLong;
        return this;
    }

    public ShopBuilder setRetailerId(String retailerId) {
        this.retailerId = retailerId;
        return this;
    }

    public ShopBuilder setOpenTime(String openTime) {
        this.openTime = openTime;
        return this;
    }

    public ShopBuilder setCloseTime(String closeTime) {
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

    public ShopBuilder setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
}
