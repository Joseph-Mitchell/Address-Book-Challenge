package com.digitalfuturesacademy.app;

import java.util.LinkedHashMap;

public class Contact {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private LinkedHashMap<String, String> details;

    public Contact(String firstName, String lastName, String phone, String email, LinkedHashMap<String, String> details) {
        if (!Validate.string(firstName) || !Validate.string(lastName) || !Validate.phone(phone)
            || !Validate.email(email) || !Validate.details(details))
            throw new IllegalArgumentException();

        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.details = details;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDetails(LinkedHashMap<String, String> details) {
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
