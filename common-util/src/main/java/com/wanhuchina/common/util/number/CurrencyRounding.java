package com.wanhuchina.common.util.number;

/**
 * Used for rounding currency values
 */
public class CurrencyRounding {

    // the amount between two consecutive values in internal representation
    private static long RATIO = 100;

    /**
     * Round down a currency value specified using internal representation
     * 
     * @param value
     *            - currency value to be rounded
     * @return currency value after rounding
     */
    public static Long roundDown(Long value) {
        return (value / RATIO) * RATIO;
    }
}