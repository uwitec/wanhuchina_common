package com.wanhuchina.common.util.json;

public class JsonCheckTree extends JsonTree {
	protected boolean checked;

	public JsonCheckTree(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
