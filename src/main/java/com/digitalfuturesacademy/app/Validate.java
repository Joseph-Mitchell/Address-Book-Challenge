package com.digitalfuturesacademy.app;

public class Validate {
    public static boolean integer(int candidate, int cap) {
        return candidate >= 0 && candidate <= cap;
    }

    public static boolean string(String candidate) {
        return candidate != null && !candidate.isBlank();
    }

    public static boolean phone(String candidate) {
        return candidate.matches("/[0-9]+/g");
    }

    public static boolean email(String candidate) {
        return true;
    }

    public static boolean yesNo(String candidate) {
        return true;
    }
}
