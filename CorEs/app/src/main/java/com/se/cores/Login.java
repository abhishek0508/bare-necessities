package com.se.cores;

import androidx.annotation.NonNull;

class Login {
    private final String email;
    private final String password;
    private final Boolean status;
    Login(@NonNull LoginBuilder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.status = builder.status;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getStatus() { return status; }
}
class LoginBuilder {
    String email;
    String password;
    Boolean status;

    public LoginBuilder() {}

    LoginBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    LoginBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    LoginBuilder setStatus(Boolean status)
    {
        this.status = status;
        return this;
    }

    public Login build() {
        Login login = new Login(this);
        //validateUserObject(customer);
        return login;
    }

    private void validateUserObject(Customer customer) {
        //Do some basic validations to check
        //if user object does not break any assumption of system
    }
}
