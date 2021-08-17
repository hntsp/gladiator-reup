package com.codecool.gladiator.util;

import java.util.List;
import java.util.Random;

public class RandomUtils {

    private static final Random RANDOM = new Random();

    /**
     * Returns a random item from a list of strings
     *
     * @param list the list to choose from
     * @return the random item
     */
    public static String getRandomItemFrom(List<String> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

    /**
     * Returns a random integer percentage between 0 and 100
     *
     * @return percentage
     */
    public static int getRandomPercentage() {
        return RANDOM.nextInt(101); // from 0 to 100 inclusive
    }

    /**
     * Generates random integer between the inclusive bounds
     *
     * @param lower the lower bound
     * @param upper the upper bound
     * @return the random integer
     */
    public static int getRandomBetween(int lower, int upper) {
        return RANDOM.nextInt(upper - lower + 1) + lower; // inclusive
    }

    /**
     * Generates random double between the bounds
     *
     * @param lower the lower bound
     * @param upper the upper bound
     * @return the random number
     */
    public static double getRandomBetween(double lower, double upper) {
        return RANDOM.nextDouble() * (upper - lower) + lower;
    }

}
