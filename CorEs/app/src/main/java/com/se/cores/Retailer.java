package com.se.cores;

// At first upload image using storage reference, get download url, store the download url here
// after creating retailer instance pass that to the database adapter for uploading in firebase.

public class Retailer {

    private final String image_url;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;

    private Retailer(RetailerBuilder builder){
        this.image_url = builder.image_url;
        this.firstName = builder.firstName;
        this.phoneNumber = builder.phoneNumber;
        this.lastName = builder.lastName;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getName() {
        return firstName+""+lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Retailer{" +
                "image_url='" + image_url + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public static class RetailerBuilder
    {
        private final String firstName;
        private final String lastName;
        private String image_url;
        private String phoneNumber;

        public RetailerBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public RetailerBuilder imageUrl(String image_url){
            this.image_url = image_url;
            return this;
        }

        public RetailerBuilder phoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Retailer build() {
            Retailer retailer =  new Retailer(this);
            validateUserObject(retailer);
            return retailer;
        }

        private void validateUserObject(Retailer retailer) {
            //Do some basic validations to check
            //if user object does not break any assumption of system
        }
    }

}

//  sample code for creation retailer instance
//  Retailer retailer = new Retailer.RetailerBuilder("Abhishek", "Gorisaria")
//            .imageUrl("www.ddsdsdsd.com/firebase")
//            .phoneNumber("1234567")
//            .build();