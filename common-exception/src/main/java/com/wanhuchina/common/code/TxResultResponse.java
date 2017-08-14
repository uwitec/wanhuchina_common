package com.wanhuchina.common.code;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * <p>
 * 接口返回给客户端的结果，通过json形式返回，spring会将该类转换成json
 * 其中的code属性是指的返回结果的编码，请参考
 * </p>
 * @author shenguanhao@che001.com
 *
 * @param <T> 返回结果中data的类型
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TxResultResponse<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 返回结果编码
	 */
	private Integer code;
	/**
	 * 返回结果信息
	 */
	private String msg;
	/**
	 * 返回结果的数据
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private T data;

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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 构造-无参
	 */
	public TxResultResponse() {
	}

	/**
	 * 构造
	 * @param msgCode
	 * @param msg
     */
	public TxResultResponse(CommonCode msgCode, String msg) {
		this.code = msgCode.getCode();
		this.msg = msg;
	}

	/**
	 * 构造
	 * @param code
	 * @param msg
     */
	public TxResultResponse(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public TxResultResponse(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}


	public static TxResultResponse getSystemErrorResponse() {
		TxResultResponse msgResponse = new TxResultResponse(CommonCode.SERVER_ERROR,"系统异常");
		return msgResponse;
	}

	public boolean isSuccessful() {
		if (code == null) {
			return false;
		}
		return code == CommonCode.SUCCESS.getCode();
	}

	public String toString() {
		return "code:"+code+",msg:"+msg+",data:"+data;
	}
}
