package com.digitalfuturesacademy.app;

import java.util.ArrayList;

public class AddressBook {
    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public Contact getContact(int index) {
        return contacts.get(index);
    }

    public void addContact(Contact contact) throws IllegalArgumentException {
        if (contact == null) throw new IllegalArgumentException();

        contacts.add(contact);
    }
}
