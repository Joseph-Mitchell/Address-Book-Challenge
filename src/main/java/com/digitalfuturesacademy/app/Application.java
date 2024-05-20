package com.digitalfuturesacademy.app;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws Exception {
        AddressBook addressBook = new AddressBook();

        LinkedHashMap<String, String> details1 = new LinkedHashMap<>();
        Contact contact1 = new Contact("Joseph", "Mitchell", "01234567890", "joseph@example.com", details1);

        LinkedHashMap<String, String> details2 = new LinkedHashMap<>();
        details2.put("Company", "Digital Futures");
        Contact contact2 = new Contact("Joseph", "Lastname", "09876543210", "joseph@gmail.com", details2);

        LinkedHashMap<String, String> details3 = new LinkedHashMap<>();
        details3.put("Species", "Unknown");
        details3.put("Motive", "Unknown");
        details3.put("Current Location", "Very close by");
        Contact contact3 = new Contact("Unknown", "Entity", "00000000000", "who@where.how", details3);

        addressBook.addContact(contact1);
        addressBook.addContact(contact2);
        addressBook.addContact(contact3);

        UserInteraction.mainMenu(addressBook);
    }
}
