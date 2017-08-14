package com.wanhuchina.common.util.uuid;

import java.util.UUID;

/**
 * UUID Generator class
 * 
 * @author Sorin Cazacu
 *
 */
public class UUIDGenerator {
     
    public static final String UUID_PATTERN = "([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-(3|4)[0-9a-fA-F]{3}-(8|9|A|B|a|b)[0-9a-fA-F]{3}-[0-9a-fA-F]{12})";
    
    /**
     * Create new UUID string
     * 
     * @return
     */
    public static String newUUID() {
        return UUID.randomUUID().toString();
    }

}
