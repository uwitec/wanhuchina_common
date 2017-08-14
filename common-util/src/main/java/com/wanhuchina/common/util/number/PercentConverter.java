package com.wanhuchina.common.util.number;

/**
 * Created by adrian.labont on 5/12/14.
 */
public class PercentConverter {

    private static int DECIMALS = 4;

    public static Long fromExternal(String value) {
        return NumberConverter.fromExternal(value, DECIMALS);
    }


    public static String toExternal(Long value) {
        return NumberConverter.toExternal(value, DECIMALS);
    }
}
