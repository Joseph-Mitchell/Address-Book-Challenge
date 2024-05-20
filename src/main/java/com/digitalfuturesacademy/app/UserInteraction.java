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
        String phone = InputReceiver.receivePhone(addressBook);
        System.out.println("Email:");
        String email = InputReceiver.receiveEmail();
        LinkedHashMap<String, String> details = InputReceiver.receiveDetails();

        Contact contact = new Contact(firstName, lastName, phone, email, details);

        ContactPrinter.printContact(contact);
        System.out.println("Add this contact? (y/n):");

        if (InputReceiver.receiveYesNo()) {
            addressBook.addContact(contact);
            System.out.println("Contact was added");
        } else {
            System.out.println("Contact was not added");
        }
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

        System.out.println("Please choose contact by number");
        return InputReceiver.receiveInt(contacts.size() - 1);
    }

    public static void removeContact(AddressBook addressBook) {
        try {
            int index = chooseContact(addressBook.getContacts());
            ContactPrinter.printContact(addressBook.getContact(index));
            System.out.println("Remove this contact? (y/n):");
            if (InputReceiver.receiveYesNo()) {
                addressBook.removeContact(index);
                System.out.println("Contact removed");
            } else {
                System.out.println("Contact not removed");
            }
        } catch (IllegalStateException e) { System.out.println(e.getMessage()); }
    }

    private static void addDetail(Contact contact) {
        String[] detail = InputReceiver.receiveDetail();
        contact.addDetail(detail[0], detail[1]);
    }

    private static void removeDetail(Contact contact) {
        System.out.println("Enter the name of the detail you want to remove (case sensitive):");
        contact.removeDetail(InputReceiver.receiveString());
        System.out.println("If detail existed, it was removed:");
    }

    private static void editDetail(Contact contact) {
        System.out.println("Enter the name of the detail you want to edit (case sensitive):");
        String key = InputReceiver.receiveString();
        System.out.println("Enter new value for the detail:");
        contact.setDetail(key, InputReceiver.receiveString());
        System.out.println("If detail existed, it was edited:");
    }

    private static void chooseDetailOption(Contact contact) {
        System.out.println("Do you want to add a new custom detail? (y/n):");
        if (InputReceiver.receiveYesNo()) {
            addDetail(contact);
            return;
        }
        System.out.println("Do you want to remove a custom detail? (y/n):");
        if (InputReceiver.receiveYesNo()) {
            removeDetail(contact);
            return;
        }
        editDetail(contact);
    }

    private static void chooseEdit(int choice, Contact contact, AddressBook addressBook) {
        switch (choice) {
            case 0:
                System.out.println("Enter new First Name:");
                contact.setFirstName(InputReceiver.receiveString());
                break;
            case 1:
                System.out.println("Enter new Last Name:");
                contact.setLastName(InputReceiver.receiveString());
                break;
            case 2:
                System.out.println("Enter new Phone:");
                contact.setPhone(InputReceiver.receivePhone(addressBook));
                break;
            case 3:
                System.out.println("Enter new Email:");
                contact.setEmail(InputReceiver.receiveEmail());
                break;
            case 4:
                chooseDetailOption(contact);
                break;
        }
        System.out.println("Attribute was edited");
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

        System.out.println("Choose an attribute to edit:");
        System.out.println("0: First Name\n1: Last Name\n2: Phone\n3: Email\n4: Edit a custom detail");
        chooseEdit(InputReceiver.receiveInt(4), contact, addressBook);
    }

    public static void findContact(AddressBook addressBook) {
        if (addressBook.getContacts().isEmpty()) {
            System.out.println("There are no contacts in the address book.");
            return;
        }

        System.out.println("Search by name:");
        ContactPrinter.printMatchingContacts(addressBook.getContacts(), InputReceiver.receiveString());
    }
}
