package com.digitalfuturesacademy.app;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class InputReceiver {
    private static Scanner input = new Scanner(System.in);

    public static void setInput(Scanner input) {
        InputReceiver.input = input;
    }

    public static int receiveInt(int cap) throws IllegalArgumentException {
        if (cap < 0) throw new IllegalArgumentException("Cap cannot be negative");

        String candidate;
        while(true) {
            try {
                if (Validate.integer(candidate = input.nextLine(), cap)) break;
            } catch (Exception ignored) {}
            System.out.printf("Please enter a number between 0 and %s%n", cap);
        }

        return Integer.parseInt(candidate);
    }

    public static String receiveString() {
        String candidate;
        while (!Validate.string(candidate = input.nextLine())) {
            System.out.println("Please enter at least one character");
        }

        return candidate;
    }

    public static String receivePhone(AddressBook addressBook) {
        String candidate;
        WHILE:
        while (true) {
            if(!Validate.phone(candidate = input.nextLine())) {
                System.out.println("Please enter a number");
                continue;
            }
            for (Contact c : addressBook.getContacts()) {
                if (c.getPhone().equals(candidate)) {
                    System.out.println("Phone already used by another contact");
                    continue WHILE;
                };
            }
            break;
        }
        return candidate;
    }

    public static String receiveEmail(AddressBook addressBook) {
        String candidate;

        WHILE:
        while (true) {
            if(!Validate.email(candidate = input.nextLine())) {
                System.out.println("Please enter an email (e.g.: person@example.com)");
                continue;
            }
            for (Contact c : addressBook.getContacts()) {
                if (c.getEmail().equals(candidate)) {
                    System.out.println("Email already used by another contact");
                    continue WHILE;
                };
            }
            break;
        }

        return candidate;
    }

    public static boolean receiveYesNo() {
        String candidate;
        while (!Validate.yesNo(candidate = input.nextLine())) {
            System.out.println("Please enter 'y' or 'n'");
        }

        return candidate.strip().toLowerCase().charAt(0) == 'y';
    }

    public static String[] receiveDetail() {
        System.out.println("Detail Type (e.g. Address, Nickname, Favourite Color):");
        String key = receiveString();
        System.out.println("Enter Detail:");
        String value = receiveString();

        return new String[] {key, value};
    }

    public static LinkedHashMap<String, String> receiveDetails() {
        LinkedHashMap<String, String> details = new LinkedHashMap<String, String>();

        while(true) {
            System.out.println("Add an extra detail? (y/n):");
            if (!receiveYesNo()) return details;

            String[] detail = receiveDetail();
            System.out.printf("%s: %s%n", detail[0], detail[1]);
            System.out.println("Add this detail? (y/n):");

            if (receiveYesNo()) {
                details.put(detail[0], detail[1]);
                System.out.println("Detail added");
            } else {
                System.out.println("Detail not added");
            }
        }
    }
}
