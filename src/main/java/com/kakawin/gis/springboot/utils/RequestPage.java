package com.kakawin.gis.springboot.utils;

import java.util.Map;

import com.kakawin.gis.springboot.config.Constant;

public class RequestPage {

	private int page = 1;

	private int size;

	public RequestPage() {
	}

	public RequestPage(Map<String, Object> map) {
		String page = (String) map.get("page");
		String size = (String) map.get("size");
		this.page = Integer.parseInt(page);
		this.size = Integer.parseInt(size);
	}

	public int getPage() {
		return page < 1 ? 1 : page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size < 1 ? Constant.defaultPageSize : (size > Constant.maxPageSize ? Constant.maxPageSize : size);
	}

	public void setSize(int size) {
		this.size = size;
	}

}
