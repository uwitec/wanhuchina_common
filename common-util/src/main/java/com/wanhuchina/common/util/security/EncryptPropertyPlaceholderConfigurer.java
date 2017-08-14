package com.wanhuchina.common.util.security;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * <p>Copyright© 2013-2016 AutoChina International Ltd. All rights reserved.</p>
 * property属性的加密和解密
 *
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private String[] encryptPropNames = {"jdbc.username", "jdbc.password"};

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName, propertyValue)) {
            return EncryptUtil.decrypt(propertyValue);
        }
        return propertyValue;
    }

    /**
     * 是否是加密的属性
     *
     * @param propertyName
     * @return
     */
    private boolean isEncryptProp(String propertyName, String propertyValue) {
        for (String encryptName : encryptPropNames) {
            //如果属性名称在列表中，并且属性的value是enc_开头的则证明是加密过的属性
            if (encryptName.equals(propertyName) && propertyValue.startsWith(EncryptUtil.ENCODE_PREFIX)) {
                return true;
            }
        }
        return false;
    }
}
