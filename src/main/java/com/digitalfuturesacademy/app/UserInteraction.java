package com.digitalfuturesacademy.app;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UserInteraction {
    public static void mainMenu(AddressBook addressBook) {
        int choice = InputReceiver.receiveInt(4);

        switch(choice) {
            case 0:
                displayContacts(addressBook);
                break;
            case 1:
                addContact(addressBook);
                break;
            case 2:
                removeContact(addressBook);
                break;
            case 3:
                editContact(addressBook);
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

    public static void displayContacts(AddressBook addressBook) {
        ContactPrinter.printAllContacts(addressBook.getContacts());
    }

    private static int chooseContact(ArrayList<Contact> contacts) {
        if (contacts.isEmpty())
            throw new IllegalStateException("There are no contacts in the address book.");

        ContactPrinter.printAllContacts(contacts);

        return InputReceiver.receiveInt(contacts.size() - 1);
    }

    public static void removeContact(AddressBook addressBook) {
        try { addressBook.removeContact(chooseContact(addressBook.getContacts())); }
        catch (IllegalStateException e) { System.out.println(e.getMessage()); }
    }

    public static void editContact(AddressBook addressBook) {
        Contact contact;

        try {
            contact = addressBook.getContact(chooseContact(addressBook.getContacts()));
            ContactPrinter.printContact(contact);
        }
        catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return;
        }

        switch (InputReceiver.receiveInt(4)) {
            case 0:
                contact.setFirstName(InputReceiver.receiveString());
                break;
            case 1:
                contact.setLastName(InputReceiver.receiveString());
                break;
            case 2:
                contact.setPhone(InputReceiver.receivePhone());
                break;
        }
    }

    public static void findContact(AddressBook addressBook) {}
}
