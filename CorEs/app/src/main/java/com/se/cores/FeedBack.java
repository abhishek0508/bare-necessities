package com.se.cores;

public class FeedBack {
    private String shopID;
    private int trueStatusYes;
    private int trueStatusNo;
    private int itemAvailabilityYes;
    private int itemAvailabilityNo;

    FeedBack(FeedbackBuilder builder) {
        this.shopID = builder.shopID;
        this.trueStatusYes = builder.trueStatusYes;
        this.trueStatusNo = builder.trueStatusNo;
        this.itemAvailabilityYes = builder.itemAvailabilityYes;
        this.itemAvailabilityNo = builder.itemAvailabilityNo;
    }

    public String getShopID() {
        return shopID;
    }

    public int getTrueStatusYes() {
        return trueStatusYes;
    }

    public int getTrueStatusNo() {
        return trueStatusNo;
    }

    public int getItemAvailabilityYes() {
        return itemAvailabilityYes;
    }

    public int getItemAvailabilityNo() {
        return itemAvailabilityNo;
    }
}

class FeedbackBuilder {
    String shopID;
    int trueStatusYes;
    int trueStatusNo;
    int itemAvailabilityNo;
    int itemAvailabilityYes;

    public FeedbackBuilder() {}

    public FeedbackBuilder setShopID(String shopID) {
        this.shopID = shopID;
        return this;
    }

    public FeedbackBuilder setTrueStatusYes(int trueStatusYes) {
        this.trueStatusYes = trueStatusYes;
        return this;
    }

    public FeedbackBuilder setTrueStatusNo(int trueStatusNo) {
        this.trueStatusNo = trueStatusNo;
        return this;
    }

    public FeedbackBuilder setItemAvailabilityYes(int itemAvailabilityYes) {
        this.itemAvailabilityYes = itemAvailabilityYes;
        return this;
    }

    public FeedbackBuilder setItemAvailabilityNo(int itemAvailabilityNo) {
        this.itemAvailabilityNo = itemAvailabilityNo;
        return this;
    }

    public FeedBack build() {
        return new FeedBack(this);
    }
}