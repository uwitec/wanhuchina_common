package com.wanhuchina.common.util.weixin.cgi;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * <p>Copyright© 2013-2016 AutoChina International Ltd. All rights reserved.</p>
 * <p>通用的基础DO,所有的DO类都要继承这个类,实现了序列化标识接口并且重写了toString方法,继承这个DO类之后就不用重写toString方法</p>
 */
public class BaseDO implements Serializable {
    public static final byte LOGICAL_DEL_ACTIVE = 0;
    public static final byte LOGICAL_DEL_DELETED = 1;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
