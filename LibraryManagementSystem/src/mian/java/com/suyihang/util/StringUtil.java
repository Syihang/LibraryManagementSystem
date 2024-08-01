package com.suyihang.util;

public class StringUtil {

    public static boolean empty(String s) {
        return s == null || s.isEmpty() || s.trim().equals("");
    }
}
