package com.wanhuchina.common.util.json;

public class JsonObjectPage extends JsonPage {

	private Object data;
	public static JsonObjectPage createJsonObjectPage(Object data) {
		JsonObjectPage jp = new JsonObjectPage();
		jp.setSuccess(true);
		jp.setData(data);
		return jp;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}