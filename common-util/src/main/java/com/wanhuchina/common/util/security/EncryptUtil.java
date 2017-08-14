package com.wanhuchina.common.util.security;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

/**
 * Created by rares.nichita on 7/17/14.
 */
public class EncryptUtil {

    private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
    public static final String ENCODE_PREFIX = "enc_";
    private static final String ALGORITHM = "PBEWithMD5AndDES";
    private static final String ENCODING = "UTF-8";
    public static final char[] ENCRYPTION_KEY = "AutoChinaEncryptionKey".toCharArray();
    public static final int ITERATION_COUNT = 20;
    private static final byte[] SALT = {
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
    };

    public static String encrypt(String property) {
        if (property == null) {
            logger.warn("Null text to encrypt");
            return null;
        }
        try {
            SecretKey key = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(new PBEKeySpec(ENCRYPTION_KEY));
            Cipher pbeCipher = Cipher.getInstance(ALGORITHM);
            pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, ITERATION_COUNT));
            return ENCODE_PREFIX + base64Encode(pbeCipher.doFinal(property.getBytes(ENCODING)));
        } catch (UnsupportedEncodingException e) {
            logger.warn("Error encrypting text" + e.getMessage());
        } catch (GeneralSecurityException e) {
            logger.warn("Error encrypting text" + e.getMessage());
        }
        return null;
    }

    private static String base64Encode(byte[] bytes) {
        Base64 base64 = new Base64();
        return base64.encodeToString(bytes);
    }

    public static String decrypt(String property) {
        if (property == null) {
            logger.warn("Null text to decrypt");
            return null;
        }
        try {
            SecretKey key = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(new PBEKeySpec(ENCRYPTION_KEY));
            Cipher pbeCipher = Cipher.getInstance(ALGORITHM);
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, ITERATION_COUNT));
            return new String(pbeCipher.doFinal(base64Decode(property.substring(ENCODE_PREFIX.length()))), ENCODING);
        } catch (GeneralSecurityException e) {
            logger.warn("Error decrypting text" + e.getMessage());
        } catch (IOException e) {
            logger.warn("Error decrypting text" + e.getMessage());
        }
        return null;
    }

    private static byte[] base64Decode(String property) {
        Base64 base64 = new Base64();
        return base64.decode(property);
    }

    public static void main(String[] args) {
        System.out.println(EncryptUtil.encrypt("platform"));
        String jiami = EncryptUtil.encrypt("platform");
        System.out.println(EncryptUtil.decrypt(jiami));
    }

}
