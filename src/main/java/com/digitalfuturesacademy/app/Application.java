package com.digitalfuturesacademy.app;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();

        UserInteraction.mainMenu(addressBook);
    }
}
