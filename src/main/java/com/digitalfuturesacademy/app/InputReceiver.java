package com.digitalfuturesacademy.app;

import java.util.LinkedHashMap;

public class InputReceiver {
    public static int receiveInt(int cap) throws IllegalArgumentException {
        if (cap < 0) throw new IllegalArgumentException("Cap cannot be negative");

        return 0;
    }

    public static String receiveString() {
        return "";
    }

    public static String receivePhone() {
        return "";
    }

    public static String receiveEmail() {
        return "";
    }

    public static LinkedHashMap<String, String> receiveDetails() {
        return new LinkedHashMap<String, String>();
    }
}
