package com.wanhuchina.common.util.number;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Sorin Cazacu
 */
public class CurrencyConverter {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyConverter.class);
    
    private static int DECIMALS = 2;


    /**
     * Convert from a two decimal currency value to internal representation
     *
     * @param value external string value
     * @return internal representation
     */
    public static Long fromExternal(String value) {
        return NumberConverter.fromExternal(value, DECIMALS);
    }


    /**
     * Convert from internal currency to two decimal string value
     *
     * @param value internal long value
     * @return the external representation
     */
    public static String toExternal(Long value) {
        return NumberConverter.toExternal(value, DECIMALS);
    }
    
    //Processing amount reserved 2 decimal places, by Andy, at 2014-11-11
    /**
     * Convert from internal currency to two decimal string value, often used to remove the decimal part of a third
     * such as: 3.016  -> 3.01
     * @param amount value internal long value
     * @return the external representation
     */
    public static Long totalAmount2External(Long amount){
        logger.debug("begin processing amount: {}", amount);
        String amount1 = CurrencyConverter.toExternal(amount);
        long amount2 = CurrencyConverter.fromExternal(amount1);
       
        logger.debug("processing amount reserved 2 decimal, amount original:{}, amount before:{}, amount after:{}",amount,amount1,amount2);
        return amount2;
    }
    /**
     * Currency Convert Negative add by kael
     * such as : -126.02 -> -1260200 , 12306 -> 123060000
     * @param value
     * @return Long
     */
    public static Long fromExternalNegative(String value) {
    	
    	String parts[] = value.split("-");
		Long afFunds = 0L;
		if (parts.length == 2) {
			long af = CurrencyConverter.fromExternal(parts[1]);
			BigDecimal amount = BigDecimal.valueOf(af).multiply(BigDecimal.valueOf(-1L));
			afFunds = amount.longValue();
		} else {
			afFunds = CurrencyConverter.fromExternal(parts[0]);
		}
    	
    	return afFunds;
    }
    
    /**
     * 不管正负，转换为金额*10000
     * @param value
     * @return
     */
    public static Long fromExternalIgnore(String value) {
    	BigDecimal aBigDecimal = new BigDecimal(value);
    	aBigDecimal = aBigDecimal.multiply(BigDecimal.valueOf(10000));
    	return aBigDecimal.longValue();    	 
    }
    /**
     * 不管正负，转换为金额/10000
     * @param value
     * @return
     */
    public static String toExternalIgnore(Long value) {
    	BigDecimal aBigDecimal = new BigDecimal(value);
    	aBigDecimal = aBigDecimal.divide(BigDecimal.valueOf(10000));
    	DecimalFormat df = new DecimalFormat("0.00");
		return df.format(aBigDecimal);
    }
    
    public static void main(String[] args) {
    	System.out.println(toExternalIgnore(1000000l));
    	System.out.println(toExternalIgnore(0l));
    	System.out.println(toExternalIgnore(-1200000l));
    	System.out.println(fromExternalIgnore("-120.20"));
    	System.out.println(fromExternalIgnore("120.20"));
    }
    
    
}
