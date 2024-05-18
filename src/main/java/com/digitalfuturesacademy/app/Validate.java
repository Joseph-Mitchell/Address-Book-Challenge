package com.digitalfuturesacademy.app;

import java.util.LinkedHashMap;

public class Validate {
    public static boolean integer(int candidate, int cap) {
        return candidate >= 0 && candidate <= cap;
    }

    public static boolean string(String candidate) {
        return candidate != null && !candidate.isBlank();
    }

    public static boolean phone(String candidate) {
        return candidate != null && candidate.matches("[0-9]+");
    }

    public static boolean email(String candidate) {
        return candidate != null && candidate.matches("[a-z]+@[a-z]+\\.[a-z]+");
    }

    public static boolean yesNo(String candidate) {
        return candidate != null && candidate.matches("[yn]");
    }

    public static boolean details(LinkedHashMap<String, String> candidate) {
        for (String s : candidate.keySet()) {
            if (s.isBlank()) return false;
        }
        for (String s : candidate.values()) {
            if (s.isBlank()) return false;
        }

        return true;
    }
}
