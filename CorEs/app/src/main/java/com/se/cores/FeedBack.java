package com.se.cores;

public class FeedBack {
    private String shopID;  // For that shop for which the feedback is given
    private boolean trueStatus;
    private boolean itemAvailability;

    FeedBack(FeedbackBuilder builder) {
        this.shopID = builder.shopID;
        this.itemAvailability = builder.itemAvailability;
        this.trueStatus = builder.trueStatus;
    }

    public String getShopID() {
        return shopID;
    }

    public boolean isItemAvailability() {
        return itemAvailability;
    }

    public boolean isTrueStatus() {
        return trueStatus;
    }
}

class FeedbackBuilder {
    String shopID;
    boolean trueStatus;
    boolean itemAvailability;

    public FeedbackBuilder() {}

    public FeedbackBuilder setShopID(String shopID) {
        this.shopID = shopID;
        return this;
    }

    public FeedbackBuilder setItemAvailability(boolean itemAvailability) {
        this.itemAvailability = itemAvailability;
        return this;
    }

    public FeedbackBuilder setTrueStatus(boolean trueStatus) {
        this.trueStatus = trueStatus;
        return this;
    }

    public FeedBack build() {
        return new FeedBack(this);
    }
}