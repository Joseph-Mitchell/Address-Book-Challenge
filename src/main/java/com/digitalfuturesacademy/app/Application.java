package com.digitalfuturesacademy.app;

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

    public static void addContact() {}

    public static void displayContacts() {}

    public static void removeContact() {}

    public static void editContact() {}

    public static void findContact() {}
}
