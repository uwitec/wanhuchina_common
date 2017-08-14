package com.wanhuchina.common.util.json;

import java.io.Serializable;
import java.util.Map;

public class JsonTree implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1059670202477183398L;
	protected String id; // ID
	protected String text; // 节点显示
	protected String iconCls;
	protected boolean leaf;
	protected Map<String, Object> extraParams;

	public JsonTree() {
	}

	public JsonTree(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Map<String, Object> getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(Map<String, Object> extraParams) {
		this.extraParams = extraParams;
	}
}
