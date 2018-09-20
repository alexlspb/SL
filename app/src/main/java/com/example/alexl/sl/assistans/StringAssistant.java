package com.example.alexl.sl.assistans;

import android.widget.EditText;

/**
 * Класс помогает работать со строками
 * содержит статические методы, что позволяет использовть его из
 * любой части приложения
 */

public abstract class StringAssistant {

    public static StringBuilder getStringBuilder(String... args) {
        StringBuilder sb = new StringBuilder();
        for (String arg:args) {
            sb.append(arg);
        }
        return sb;
    }

    public static String getString(String... args) {
        StringBuilder sb = new StringBuilder();
        for (String arg:args) {
            sb.append(arg);
        }
        return sb.toString();
    }

    public static String getStringOfEditText(EditText et) {
        return et.getText().toString();
    }

    public static int getIntegerOfEditText(EditText et) {
        String text = et.getText().toString();
        int number = 0;
        if (!text.equals("")) {
            number = Integer.parseInt(text);
        }
        return number;
    }

    public static String firstUpperCase(String word) {
        if(word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }
}
