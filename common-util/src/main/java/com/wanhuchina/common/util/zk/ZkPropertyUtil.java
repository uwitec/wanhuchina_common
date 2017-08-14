package com.wanhuchina.common.util.zk;

import java.util.Properties;

public class ZkPropertyUtil {
	private final static Properties CONF = new Properties();

	public static Properties getAll(){
		return CONF;
	}
	
	public static void set(String key,String value){
		CONF.setProperty(key, value);
	}

	public static String get(String string) {
		return getAll().getProperty(string);
	}
	public static void remove(String key){
		CONF.remove(key);
	}
}
