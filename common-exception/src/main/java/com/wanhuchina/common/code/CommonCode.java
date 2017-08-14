package com.wanhuchina.common.code;

/**
 * <p>
 * 前台通用错误码,返回码统一
 * 大家互相调用的时候就可不必转码
 * 即使转码也很轻松
 * </p>
 *
 */
public enum CommonCode {
    //通用的成功
    SUCCESS(10000, "操作成功"),
    //吴无力判断fasle
    ERROR(10001,"返回值为fasle"),
    //服务器错误
    SERVER_ERROR(99999, "服务内部异常"),
    //秘钥配对错误
    KEY_ERROR(77777, "秘钥配对错误"),
    //秘钥配对错误
    HTTP_ERROR(80000, "远程调用错误"),
    //socket
    SOCKET_ONLINE_ERROR(80001,"socket断开"),
    //参数错误
    PARAM_ERROR(90000,"参数错误"),
    //参数错误
    DATA_NOT_FOUND(90004,"没有记录"),
    //数据库查询错误
    SQL_SELECT_ERROR(90001,"数据库查询错误"),
    //数据库查询错误
    SQL_UPD_ERROR(90002,"数据库修改错误"),
    //数据库查询错误
    SQL_DEL_ERROR(90003,"数据库删除错误"),

    ;

    /**
     * 编码
     */
    private Integer code;

    /**
     * 描述信息
     */
    private String msg;

    CommonCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
