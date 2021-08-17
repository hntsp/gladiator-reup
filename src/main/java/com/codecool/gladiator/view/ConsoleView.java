package com.codecool.gladiator.view;

import java.util.Scanner;

/**
 * Basic console view implementation
 */
public class ConsoleView implements Viewable {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void display(String text) {
        System.out.println(text);
    }

    @Override
    public int getNumberBetween(int lower, int upper) {
        int number = 0;
        while (!(number >= lower && number <= upper)) {
            String input = scanner.next();

            // Try parsing the string input into a number
            try {
                number = Integer.parseInt(input);
            } catch (NumberFormatException ignored) {
            }
        }
        return number;
    }

}
