package com.alanthechatbot.utils;

public class PrintUtils {
    private static final int LINE_BREAK_LENGTH = 45;

    private PrintUtils() {
    }

    ;

    public static void printLineBreak() {
        for (int i = 0; i < LINE_BREAK_LENGTH; i++) {
            System.out.print('-');
        }
        System.out.print("\n");
    }

    public static void printIntro() {
        printLineBreak();
        System.out.println("Hello! I'm Alan");
        System.out.println("What can I do for you?");
        printLineBreak();
    }
}
