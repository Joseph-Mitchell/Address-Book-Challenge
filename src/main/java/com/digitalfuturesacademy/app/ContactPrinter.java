package com.digitalfuturesacademy.app;

import java.util.ArrayList;

public class ContactPrinter {
    public static void printAllContacts(ArrayList<Contact> contacts) throws IllegalArgumentException {
        if (contacts == null) throw new IllegalArgumentException();
    }
}
