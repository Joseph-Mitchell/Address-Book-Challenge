package com.digitalfuturesacademy.app;

import java.util.LinkedHashMap;

public class Application {
    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();

        UserInteraction.mainMenu(addressBook);
    }
}
