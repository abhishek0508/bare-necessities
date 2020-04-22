package com.se.cores;

public class FeedBack {
    private boolean status;
    private Integer itemAvailabilityNo;
    private Integer itemAvailabilityYes;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getItemAvailabilityNo() {
        return itemAvailabilityNo;
    }

    public void setItemAvailabilityNo(Integer itemAvailabilityNo) {
        this.itemAvailabilityNo = itemAvailabilityNo;
    }

    public Integer getItemAvailabilityYes() {
        return itemAvailabilityYes;
    }

    public void setItemAvailabilityYes(Integer itemAvailabilityYes) {
        this.itemAvailabilityYes = itemAvailabilityYes;
    }
}
