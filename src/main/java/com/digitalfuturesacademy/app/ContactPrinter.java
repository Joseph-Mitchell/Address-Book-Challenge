package com.digitalfuturesacademy.app;

import java.util.ArrayList;

public class ContactPrinter {
    public static void printContact(Contact contact) {

    }

    public static void printAllContacts(ArrayList<Contact> contacts) throws IllegalArgumentException {
        if (contacts == null) throw new IllegalArgumentException();

        for (Contact c : contacts) {
            printContact(c);
        }
    }
}
