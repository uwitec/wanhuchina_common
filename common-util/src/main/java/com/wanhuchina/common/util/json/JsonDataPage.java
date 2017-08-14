package com.wanhuchina.common.util.json;

import java.util.ArrayList;
import java.util.List;

public class JsonDataPage extends JsonPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3957157190622228638L;
	private long total;
	private List<Object> rows = new ArrayList<Object>();

	public static JsonDataPage createJsonDataPage() {
		JsonDataPage jdp = new JsonDataPage();
		jdp.setSuccess(true);
		return jdp;
	}

	public static JsonDataPage createJsonDataPage(List<?> rows) {
		JsonDataPage jdp = createJsonDataPage();
		jdp.setRows(rows);
		return jdp;
	}

	public static JsonDataPage createJsonDataPage(List<?> rows, long total) {
		JsonDataPage jdp = createJsonDataPage(rows);
		jdp.setTotal(total);
		return jdp;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<Object> getRows() {
		return rows;
	}

	@SuppressWarnings("unchecked")
	public void setRows(List<?> rows) {
		this.rows = (List<Object>) rows;
	}

}