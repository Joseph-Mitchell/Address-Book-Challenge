package com.digitalfuturesacademy.app;

import java.util.ArrayList;

public class ContactPrinter {
    public static void printContact(Contact contact) throws IllegalArgumentException {
        if (contact == null) throw new IllegalArgumentException("Contact was null");

        String mainBlock = """
                --------------------
                First Name: %s
                
                Last Name: %s
                
                Phone: %s
                
                Email: %s
                --------------------""".formatted(contact.getFirstName(), contact.getLastName(),
                                                  contact.getPhone(), contact.getEmail());

        System.out.print(mainBlock);
    }

    public static void printAllContacts(ArrayList<Contact> contacts) throws IllegalArgumentException {
        if (contacts == null) throw new IllegalArgumentException("Contact list was null");

        for (Contact c : contacts) {
            printContact(c);
        }
    }
}
