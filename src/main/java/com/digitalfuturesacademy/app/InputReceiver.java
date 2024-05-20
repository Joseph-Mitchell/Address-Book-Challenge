package com.digitalfuturesacademy.app;

import java.util.*;

public class InputReceiver {
    private static Scanner input = new Scanner(System.in);

    public static void setInput(Scanner input) {
        InputReceiver.input = input;
    }

    public static int receiveInt(int cap) throws IllegalArgumentException {
        String candidate;

        if (cap < 0) {
            throw new IllegalArgumentException("Cap cannot be negative");
        }

        while(true) {
            try {
                if (Validate.integer(candidate = input.nextLine(), cap)) {
                    break;
                }
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

    /**
     * Prompts the user to input a phone number, validates the input,
     * and ensures the phone number is not already in use by another contact.
     *
     * @param contacts the list of existing contacts
     * @return a valid and unique phone number
     */
    public static String receivePhone(ArrayList<Contact> contacts) {
        String candidate;
        Set<String> phoneNumbers = new HashSet<>();
        for (Contact c : contacts) {
            phoneNumbers.add(c.getPhone());
        }

        while (true) {
            candidate = input.nextLine();
            if(!Validate.phone(candidate)) {
                System.out.println("Please enter a number");
            } else if (phoneNumbers.contains(candidate)) {
                    System.out.println("Phone already used by another contact");
            } else {
                break;
            }
        }
        return candidate;
    }

    /**
     * Prompts the user to input an email, validates the input,
     * and ensures the email is not already in use by another contact.
     *
     * @param contacts the list of existing contacts
     * @return a valid and unique email
     */
    public static String receiveEmail(ArrayList<Contact> contacts) {
        String candidate;
        Set<String> emails = new HashSet<>();
        for (Contact c : contacts) {
            emails.add(c.getEmail());
        }

        while (true) {
            candidate = input.nextLine();
            if(!Validate.email(candidate)) {
                System.out.println("Please enter an email (e.g.: person@example.com)");
            } else if (emails.contains(candidate)) {
                System.out.println("Email already used by another contact");
            } else {
                break;
            }
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
        LinkedHashMap<String, String> details = new LinkedHashMap<>();

        while(true) {
            System.out.println("Add an extra detail? (y/n):");
            if (!receiveYesNo()) {
                return details;
            }

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
