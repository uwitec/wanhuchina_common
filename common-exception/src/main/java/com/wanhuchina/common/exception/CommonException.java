package com.wanhuchina.common.exception;

import com.wanhuchina.common.code.CommonCode;

/**
 * <p>通用异常</p>
 */
public class CommonException extends RuntimeException {

    /**
     * code编码
     */
    private Integer code;

    /**
     * 异常信息,需要展示给用户的,需要小心处理
     */
    private String msg;


    public CommonException() {
        super();
    }

    /**
     * <p>自定义异常记录错误码</p>
     *
     * @param commonCode
     */
    public CommonException(CommonCode commonCode) {
        super(commonCode.getCode() + "::" + commonCode.getMsg());
        code = commonCode.getCode();
        msg = commonCode.getMsg();
    }

    /**
     * <p>记录错误码并将当时报错的异常信息传递到自定义异常中</p>
     *
     * @param commonCode
     * @param cause
     */
    public CommonException(CommonCode commonCode, Throwable cause) {
        super(commonCode.getCode() + "::" + commonCode.getMsg(), cause);
        code = commonCode.getCode();
        msg = commonCode.getMsg();
    }

    /**
     * @param code
     * @param msg
     * @param cause
     */
    public CommonException(Integer code, String msg, Throwable cause) {
        super(code + "::" + msg, cause);
        this.code = code;
        this.msg = msg;
    }

    /**
     * @param code
     * @param msg
     */
    public CommonException(Integer code, String msg) {
        super(code + "::" + msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
