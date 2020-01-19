package com.gmail.petrikov05.util;

import java.util.Random;

public class RandomUtil {

    private static final Random RANDOM = new Random();

    public static int getIntFromRange(int minValue, int maxValue) {
        return RANDOM.nextInt(maxValue + 1 - minValue) + minValue;
    }

    public static int getInt() {
        return RANDOM.nextInt();
    }

    public static boolean getBoolean() {
        return RANDOM.nextBoolean();
    }

}
