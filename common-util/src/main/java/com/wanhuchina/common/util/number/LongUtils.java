package com.wanhuchina.common.util.number;

public class LongUtils {

    /**
     * Checks if a Long is null or zero
     */
    public static boolean isNullOrZero(Long value) {
        return value == null || value == 0;
    }

    /**
     * Returns the primitive long corresponding to a Long object or zero if it is null
     */
    public static long getLongOrZero(Long value) {
        return value == null ? 0 : value.longValue();
    }
}