package com.wanhuchina.common.util.json;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JsonPage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -161310951228147504L;
	private String msg;
	private boolean success;
	private HashMap<String, Object> extParams;

	public static JsonPage createSuccess() {
		JsonPage jp = new JsonPage();
		jp.setSuccess(true);
		return jp;
	}
	public static JsonPage createFailure() {
		JsonPage jp = new JsonPage();
		jp.setSuccess(false);
		return jp;
	}
	public static JsonPage createSuccess(HashMap<String, Object> extParams) {
		JsonPage jp = new JsonPage();
		jp.setExtParams(extParams);
		jp.setSuccess(true);
		return jp;
	}

	public static JsonPage createErrorMsg(String msg) {
		JsonPage jp = new JsonPage();
		jp.setMsg(msg);
		return jp;
	}

	public static JsonPage createSuccessMsg(String msg) {
		JsonPage jp = new JsonPage();
		jp.setMsg(msg);
		jp.setSuccess(true);
		return jp;
	}

	public static JsonPage createMsg(boolean success, String msg) {
		JsonPage jp = new JsonPage();
		jp.setMsg(msg);
		jp.setSuccess(success);
		return jp;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String, Object> getExtParams() {
		return extParams;
	}

	public void setExtParams(HashMap<String, Object> extParams) {
		this.extParams = extParams;
	}

}