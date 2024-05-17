package com.digitalfuturesacademy.app;

import java.util.LinkedHashMap;

public class Application {
    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu() {
        int choice = InputReceiver.receiveInt(4);

        switch(choice) {
            case 1:
                addContact();
                break;
        }
    }

    public static void addContact() {
        String firstName = InputReceiver.receiveString();
        String lastName = InputReceiver.receiveString();
        String phone = InputReceiver.receivePhone();
        String email = InputReceiver.receiveEmail();
        LinkedHashMap<String, String> details = InputReceiver.receiveDetails();

        Contact contact = new Contact(firstName, lastName, phone, email, details);
    }

    public static void displayContacts() {}

    public static void removeContact() {}

    public static void editContact() {}

    public static void findContact() {}
}
