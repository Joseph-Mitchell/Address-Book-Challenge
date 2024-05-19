package com.digitalfuturesacademy.app;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UserInteraction {
    public static void mainMenu(AddressBook addressBook) {
        System.out.print("""
                Please choose an option:
                
                0. Display contacts
                1. Add a new contact
                2. Remove a contact
                3. Edit a contact
                4. Search contacts
                """);

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
            case 4:
                findContact(addressBook);
                break;
        }
    }

    public static void addContact(AddressBook addressBook) {
        System.out.println("First Name:");
        String firstName = InputReceiver.receiveString();
        System.out.println("Last Name:");
        String lastName = InputReceiver.receiveString();
        System.out.println("Phone:");
        String phone = InputReceiver.receivePhone();
        System.out.println("Email:");
        String email = InputReceiver.receiveEmail();
        LinkedHashMap<String, String> details = InputReceiver.receiveDetails();

        Contact contact = new Contact(firstName, lastName, phone, email, details);

        addressBook.addContact(contact);
    }

    public static void displayContacts(AddressBook addressBook) {
        if (addressBook.getContacts().isEmpty()) {
            System.out.println("There are no contacts in the address book.");
            return;
        }
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

    private static void editDetail(Contact contact) {
        if (InputReceiver.receiveYesNo()) {
            String[] detail = InputReceiver.receiveDetail();
            contact.addDetail(detail[0], detail[1]);
        }
        else if (InputReceiver.receiveYesNo()) {
            contact.removeDetail(InputReceiver.receiveString());
        }
        else {
            contact.setDetail(InputReceiver.receiveString(), InputReceiver.receiveString());
        }
    }

    private static void chooseEdit(int choice, Contact contact) {
        switch (choice) {
            case 0:
                contact.setFirstName(InputReceiver.receiveString());
                break;
            case 1:
                contact.setLastName(InputReceiver.receiveString());
                break;
            case 2:
                contact.setPhone(InputReceiver.receivePhone());
                break;
            case 3:
                contact.setEmail(InputReceiver.receiveEmail());
                break;
            default:
                editDetail(contact);
                break;
        }
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

        chooseEdit(InputReceiver.receiveInt(4), contact);
    }

    public static void findContact(AddressBook addressBook) {
        if (addressBook.getContacts().isEmpty())
            System.out.println("There are no contacts in the address book.");

        ContactPrinter.printMatchingContacts(addressBook.getContacts(), InputReceiver.receiveString());
    }
}
