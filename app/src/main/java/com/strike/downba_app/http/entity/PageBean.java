package com.strike.downba_app.http.entity;

public class PageBean {
	public int pageNo;
	public int pageSize;
	public int total;
	public int totalPage;
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public PageBean(int pageNo, int pageSize, int total, int totalPage) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.total = total;
		this.totalPage = totalPage;
	}
	public PageBean() {
		super();
	}
	@Override
	public String toString() {
		return "PageBean [pageNo=" + pageNo + ", pageSize=" + pageSize + ", total=" + total + ", totalPage=" + totalPage
				+ "]";
	}
	
}
