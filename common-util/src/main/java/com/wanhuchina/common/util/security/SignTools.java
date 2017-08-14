package com.wanhuchina.common.util.security;

import com.google.common.base.Strings;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Md5加密utils
 * @author shenguanhao
 * 2017-04-14
 */
public class SignTools {
    public static String md5(String message) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        try {
            // 与汇付宝编码一致
            byte[] btInput = message.getBytes("UTF-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * MD5 签名
     * @param content
     * @param key
     * @param charset
     * @return
     * @throws Exception
     */
    public static String getSign(String content, String key, String charset) throws Exception {
        String signData = content + key;

        if (Strings.isNullOrEmpty(charset)) {
            charset = "UTF-8";
        }
        try {
            String sign = DigestUtils.md5Hex(signData.getBytes(charset));
            return sign;
        } catch (UnsupportedEncodingException e) {
            throw new Exception(e);
        }
    }
    public static void main(String[] args) {
        System.out.println(SignTools.md5("wanhuchina-lock"));
    }
}
