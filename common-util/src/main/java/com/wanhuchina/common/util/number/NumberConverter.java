package com.wanhuchina.common.util.number;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adrian.labont on 5/12/14.
 */
public class NumberConverter {

    private static Logger log = LoggerFactory.getLogger(NumberConverter.class);

    private static int PRECISION = 4;
    private static int UNITS = (int) Math.pow(10, PRECISION);

    /**
     * Convert a value from an external format with the <code>decimals</code> precision
     */
    public static Long fromExternal(String value, int decimals) {

        if (Strings.isNullOrEmpty(value)) {
            log.error("Invalid number value {}", value);
            throw new InvalidNumberException("Invalid number to convert " + value);
        }

        String parts[] = value.split("\\.");
        if (parts.length > 2) {
            log.warn("Invalid number value {}", value);
            throw new InvalidNumberException();
        }

        try {
            long l = Long.parseLong(parts[0]);
            long d = 0;
            if (parts.length == 2 && parts[1] != null) {
                if (parts[1].length() > decimals) {
                    throw new InvalidNumberException("Invalid decimals count " + value);
                }
                d = Long.parseLong(parts[1]);

                int diff = PRECISION - parts[1].length();
                if (diff > 0) {
                    d = (long) Math.pow(10, diff) * d;
                }
            }

            // check we don't overflow
            if ((l != 0 && UNITS > Long.MAX_VALUE / Math.abs(l)) || (l * UNITS > Long.MAX_VALUE - d)) {
                log.warn("Value too large {}", value);
                throw new InvalidNumberException("Value too large " + value);
            }

            return l * UNITS + d;
        } catch (InvalidNumberException ex) {
            throw ex;
        } catch (Exception ex) {
            log.warn("Failed to convert number {}", value, ex);
            throw new InvalidNumberException(ex);
        }
    }

    /**
     * Convert from internal number to two decimal string value
     * 
     * @param value
     *            internal long value
     * @return the external representation
     */
    public static String toExternal(Long value, int decimals) {
        log.debug("Convert value {}", value);

        if (value == null) {
            log.warn("Invalid internal value {}", value);
            throw new InvalidNumberException();
        }

        if (value == 0) {
            log.debug("Converted value 0.00");
            return "0.00";
        }

        if (Math.abs(value) < (int) Math.pow(10, PRECISION - decimals)) {
            log.warn("Invalid value too small {}", value);
            throw new InvalidNumberException("Value too small " + value);
        }

        Long n = value / UNITS;
        String strN = String.valueOf(n);
        //to solve the problem of decimal negative Numbers, modified by jason,2014-11-17 
        if (value < 0 && n == 0) {
            strN="-"+strN;
        }
        Long d = Math.abs(value) % UNITS;
        d /= (long) Math.pow(10, PRECISION - decimals);
        String strD = Strings.padStart(String.valueOf(d), decimals, '0');

        log.debug("Converted value {}", strN + "." + strD);

        return strN + "." + strD;
    }
}