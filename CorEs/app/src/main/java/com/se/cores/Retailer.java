package com.se.cores;

// At first upload image using storage reference, get download url, store the download url here
// after creating retailer instance pass that to the database adapter for uploading in firebase.

public class Retailer {

    private final String image_url;
    private final String name;
    private final String phoneNumber;
    private final String email;
    private final String password;
    private String shopID;

    Retailer(RetailerBuilder builder) {
        this.image_url = builder.image_url;
        this.name = builder.name;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.password = builder.password;
        this.shopID = builder.shopID;
    }

    public String getName() {
        return name;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getShopID() {
        return shopID;
    }

    @Override
    public String toString() {
        return "Retailer{" +
                "image_url='" + image_url + '\'' +
                ", Name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

class RetailerBuilder {
    String name;
    String phoneNumber;
    String email;
    String password;
    String image_url;
    String shopID;

    public RetailerBuilder() {}

    RetailerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    RetailerBuilder setPhoneNumber(String phone) {
        this.phoneNumber = phone;
        return this;
    }

    RetailerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    RetailerBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public RetailerBuilder setImage_url(String image_url) {
        this.image_url = image_url;
        return this;
    }

    public RetailerBuilder setShopID(String shopID) {
        this.shopID = shopID;
        return this;
    }

    public Retailer build() {
        Retailer retailer = new Retailer(this);
        validateUserObject(retailer);
        return retailer;
    }

    private void validateUserObject(Retailer retailer) {
        //Do some basic validations to check
        //if user object does not break any assumption of system
    }
}

//  sample code for creation retailer instance
//  Retailer retailer = new Retailer.RetailerBuilder("Abhishek", "Gorisaria")
//            .imageUrl("www.ddsdsdsd.com/firebase")
//            .phoneNumber("1234567")
//            .build();