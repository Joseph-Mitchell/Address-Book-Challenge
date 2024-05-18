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

        int candidate;
        while(true) {
            try {
                if (Validate.integer(candidate = input.nextInt(), cap)) break;
            } catch (Exception ignored) {}
        }

        return candidate;
    }

    public static String receiveString() {
        String candidate;
        while(true) {
            if (Validate.string(candidate = input.nextLine())) break;
        }

        return candidate;
    }

    public static String receivePhone() {
        String candidate;
        while(true) {
            if (Validate.phone(candidate = input.nextLine())) break;
        }

        return candidate;
    }

    public static String receiveEmail() {
        String candidate;
        while(true) {
            if (Validate.email(candidate = input.nextLine())) break;
        }

        return candidate;
    }

    public static LinkedHashMap<String, String> receiveDetails() {
        return new LinkedHashMap<String, String>();
    }
}
