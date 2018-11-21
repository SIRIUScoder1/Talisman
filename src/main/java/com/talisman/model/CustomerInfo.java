package com.talisman.model;


public class CustomerInfo {

    private String name;
    private String address;
    private String email;
    private String phoneNumber;

    private boolean valid;

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setValid(final boolean valid) {
        this.valid = valid;
    }
}
