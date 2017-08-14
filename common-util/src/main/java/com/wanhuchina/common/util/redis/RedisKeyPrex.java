package com.wanhuchina.common.util.redis;

/**
 * <p>Copyright© 2013-2016 AutoChina International Ltd. All rights reserved.</p>
 * <p>redis中存储的key的前缀，这里是个统一的地方，为的就是防止key重复而发生覆盖</p>
 * @Author yangzhibin@che001.com
 * @Date 2016/4/12
 */
public interface RedisKeyPrex {

    /**
     * 邮件验证码缓存前缀
     */
    String MAIL_VERIFY_CODE_PRE="mail_verify_code_pre_";

    /**
     * 手机短信验证码缓存前缀
     */
    String SMS_VERIFY_CODE_PRE="sms_verify_code_pre_";
}
