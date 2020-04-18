package com.se.cores;

import androidx.annotation.NonNull;

class User {
    private final String name;
    private final String email;
    private final String password;
    private final String phoneNumber;

    User(@NonNull UserBuilder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.phoneNumber = builder.phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

class UserBuilder {
    String name;
    String phoneNumber;
    String email;
    String password;

    public UserBuilder() {}

    UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    UserBuilder setPhoneNumber(String phone) {
        this.phoneNumber = phone;
        return this;
    }

    UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        User user =  new User(this);
        validateUserObject(user);
        return user;
    }

    private void validateUserObject(User user) {
        //Do some basic validations to check
        //if user object does not break any assumption of system
    }
}