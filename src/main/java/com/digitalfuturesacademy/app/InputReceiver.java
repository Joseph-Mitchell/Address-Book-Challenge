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

    public static boolean receiveYesNo() {
        String candidate;
        while(true) {
            if (Validate.yesNo(candidate = input.nextLine())) break;
        }

        return candidate.strip().toLowerCase().charAt(0) == 'y';
    }

    public static String[] receiveDetail() {
        String key = receiveString();
        String value = receiveString();

        return new String[] {key, value};
    }

    public static LinkedHashMap<String, String> receiveDetails() {
        LinkedHashMap<String, String> details = new LinkedHashMap<String, String>();
        while(receiveYesNo()) {
            String[] detail = receiveDetail();
            details.put(detail[0], detail[1]);
        }

        return details;
    }
}
