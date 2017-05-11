package com.strike.downba_app.http.bean;

import java.util.List;


public class Guess {

	private Integer cateId;
	private String adIds;

	private List<AppAd> objs;

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public String getAdIds() {
		return adIds;
	}

	public void setAdIds(String adIds) {
		this.adIds = adIds;
	}

	public List<AppAd> getObjs() {
		return objs;
	}

	public void setObjs(List<AppAd> objs) {
		this.objs = objs;
	}

}
