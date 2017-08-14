package com.wanhuchina.common.pageinfo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * 返回列表信息
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageInfo<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6804909457635588142L;

	private List<T> list;

	private int pageIndex; // 当前页数，第一页是起始页

	private int pageSize;

	private int count;

	public PageInfo() {

	}

	public PageInfo(List<T> list, int count, int pageIndex, int pageSize) {
		this.list = list;
		this.count = count;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		if(pageSize == 0){
			return 0;
		}
		return (count % pageSize == 0) ? count / pageSize : count / pageSize + 1;
	}

	public boolean isFirstPage() {
		return pageIndex == 1;
	}

	public boolean isLastPage() {
		return pageIndex == getPageCount();
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (list != null) {
			for (T t : list) {
				sb.append(t.toString());
			}
		}
		return "PageInfo [list=" + sb.toString() + ", pageIndex=" + pageIndex + ", pageSize=" + pageSize + ", count="
				+ count + "]";
	}
}
