package com.strike.downba_app.http.bean;

import com.strike.downba_app.utils.Constance;

import java.util.HashMap;
import java.util.Map;

public class PageBean {
	private Integer pageNo;
	private Integer pageSize;
	private Integer total;
	private Integer totalPage;
	public Map<String, Object> conditions = new HashMap<>();

	public PageBean() {
	}

	public PageBean(Integer pageNo, Integer pageSize, Integer total) {
		this.pageNo = pageNo==null || pageNo < 1?1:pageNo;
		this.pageSize = pageSize == null || pageSize < 1? Constance.DEFAULT_PAGE_SIZE : pageSize;
		this.total = total == null || total < 0?0:total;
		this.totalPage = this.total % this.pageSize == 0 ? this.total / this.pageSize
				: this.total / this.pageSize + 1;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Map<String, Object> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, Object> conditions) {
		this.conditions = conditions;
	}


}
