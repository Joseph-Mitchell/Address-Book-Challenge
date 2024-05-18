package com.digitalfuturesacademy.app;

import java.util.LinkedHashMap;

public class UserInteraction {
    public static void mainMenu(AddressBook addressBook) {
        int choice = InputReceiver.receiveInt(4);

        switch(choice) {
            case 1:
                addContact(addressBook);
                break;
        }
    }

    public static void addContact(AddressBook addressBook) {
        String firstName = InputReceiver.receiveString();
        String lastName = InputReceiver.receiveString();
        String phone = InputReceiver.receivePhone();
        String email = InputReceiver.receiveEmail();
        LinkedHashMap<String, String> details = InputReceiver.receiveDetails();

        Contact contact = new Contact(firstName, lastName, phone, email, details);

        addressBook.addContact(contact);
    }

    public static void displayContacts(AddressBook addressBook) {}

    public static void removeContact(AddressBook addressBook) {}

    public static void editContact(AddressBook addressBook) {}

    public static void findContact(AddressBook addressBook) {}
}
