package com.digitalfuturesacademy.app;

public class Validate {
    public static boolean integer(int candidate, int cap) {
        if (candidate < 0) return false;
        return true;
    }

    public static boolean string(String candidate) {
        return true;
    }

    public static boolean phone(String candidate) {
        return true;
    }

    public static boolean email(String candidate) {
        return true;
    }

    public static boolean yesNo(String candidate) {
        return true;
    }
}
