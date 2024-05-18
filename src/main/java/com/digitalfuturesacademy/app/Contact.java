package com.digitalfuturesacademy.app;

import java.util.LinkedHashMap;

public class Contact {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private LinkedHashMap<String, String> details;

    public Contact(String firstName, String lastName, String phone, String email, LinkedHashMap<String, String> details) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.details = details;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public LinkedHashMap<String, String> getDetails() {
        return details;
    }
}