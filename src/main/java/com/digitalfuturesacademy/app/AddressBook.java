package com.digitalfuturesacademy.app;

public class AddressBook {
    public void addContact(Contact contact) throws IllegalArgumentException {
        if (contact == null) throw new IllegalArgumentException();
    }
}
