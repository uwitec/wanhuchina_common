package com.wanhuchina.common.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * <p>通用工具类</p>
 */
public final class CommonUtil {

    /**
     * <p>获取md5 32位小写字符串</p>
     * @param str
     * @return
     */
    public final static String MD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            char[] charArray = str.toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++)
                byteArray[i] = (byte) charArray[i];
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * <p>将多个字符串拼接获取md5 32位小写字符串</p>
     * @param strings
     * @return
     */
    public static final String MD5(String... strings){
        StringBuffer sb=new StringBuffer();
        for (String str:strings) {
            sb.append(str);
        }
        return MD5(sb.toString());
    }

    /**
     * <p>获取乐观锁version</p>
     * @return
     */
    public static final long getVersion(){
        return System.nanoTime();
    }

    /**
     * <p>获取uuid</p>
     * @return
     */
    public static final String getUUID(){
       return UUID.randomUUID().toString();
    }

    /**
     * <p>将时间按照指定的格式转换成string</p>
     * @param date
     * @param format
     * @return
     */
    public static final String dateFormat(Date date,String format){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * <p>数据乘以10000</p>
     * @param decimal
     * @return
     */
    public static final long multiply1w(BigDecimal decimal){
        return decimal.multiply(new BigDecimal(10000)).longValue();
    }


    public static void main(String[] args) {
        System.out.println(CommonUtil.dateFormat(new Date(),"yyyyMMddHHmmss"));
    }
}
