package com.wanhuchina.common.util.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable {
	private static final long serialVersionUID = 3915923353990500904L;
	/**
	 * 每页显示条数
	 */
	private int pageSize;
	/**
	 * 当前页码
	 */
	private int pageNo;
	/**
	 * 排序
	 */
	private String orderBy;
	/**
	 * 总数
	 */
	private long totalCount;
	/**
	 * 总页数
	 */
	private long totalPages;

	private List<T> data = new ArrayList<T>();

	public static <E> Page<E> createPage() {
		Page<E> page = new Page<>();
		return page;
	}

	public static <E> Page<E> createPage(int pageNo, int pageSize) {
		Page<E> page = createPage();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}

	public static <E> Page<E> createPage(int pageNo, int pageSize, String orderBy) {
		Page<E> page = createPage(pageNo, pageSize);
		page.setOrderBy(orderBy);
		return page;
	}

	public static <E> Page<E> createPage(List<E> data) {
		Page<E> page = createPage();
		page.setData(data);
		return page;
	}

	public static <E> Page<E> createPage(List<E> data, long totalCount) {
		Page<E> page = createPage();
		page.setData(data);
		page.setTotalCount(totalCount);
		return page;
	}

	public int getFirstResult() {
		return (getPageNo() - 1) * getPageSize();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalPages() {
		if (totalCount % pageSize == 0) {
			this.totalPages = totalCount / pageSize;
		} else {
			this.totalPages = totalCount / pageSize + 1;
		}
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Page [pageSize=" + pageSize + ", pageNo=" + pageNo + ", orderBy=" + orderBy + ", totalCount="
			+ totalCount + ", totalPages=" + totalPages + ", data=" + data + "]";
	}

}