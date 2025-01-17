package com.digitalfuturesacademy.app;

import java.util.ArrayList;

public class AddressBook {
    private final ArrayList<Contact> contacts = new ArrayList<>();

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public Contact getContact(int index) {
        return contacts.get(index);
    }

    public void addContact(Contact contact) throws IllegalArgumentException {
        if (contact == null) {
            throw new IllegalArgumentException();
        }

        contacts.add(contact);
    }

    public void removeContact(int index) {
        if (index < 0 || index >= contacts.size()) {
            throw new IllegalArgumentException("Index was out of range");
        }

        contacts.remove(index);
    }
}
