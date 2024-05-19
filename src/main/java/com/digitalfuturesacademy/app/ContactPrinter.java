package com.digitalfuturesacademy.app;

import java.util.ArrayList;
import java.util.Map;

public class ContactPrinter {
    public static void printContact(Contact contact) throws IllegalArgumentException {
        if (contact == null) throw new IllegalArgumentException("Contact was null");

        String output = """
                --------------------
                First Name: %s
                
                Last Name: %s
                
                Phone: %s
                
                Email: %s
                """.formatted(contact.getFirstName(), contact.getLastName(),
                                                  contact.getPhone(), contact.getEmail());

        for (Map.Entry<String, String> e : contact.getDetails().entrySet()) {
            output = output.concat("\n%s: %s\n".formatted(e.getKey(), e.getValue()));
        }

        output += "--------------------\n";

        System.out.print(output);
    }

    public static void printAllContacts(ArrayList<Contact> contacts) throws IllegalArgumentException {
        if (contacts == null) throw new IllegalArgumentException("Contact list was null");

        for (int i = 0; i < contacts.size(); i++) {
            System.out.println(i);
            printContact(contacts.get(i));
        }
    }

    public static void printMatchingContacts(ArrayList<Contact> contacts, String comparitor) throws IllegalArgumentException {
        if (!Validate.string(comparitor) || contacts == null) throw new IllegalArgumentException();

        String prepared = comparitor.strip().toLowerCase();

        for (Contact c : contacts) {
            if (c.getFirstName().toLowerCase().contains(prepared) || c.getLastName().toLowerCase().contains(prepared)) printContact(c);
        }
    }
}
