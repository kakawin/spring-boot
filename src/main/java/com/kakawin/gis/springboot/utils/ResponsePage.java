package com.kakawin.gis.springboot.utils;

import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

public class ResponsePage<T> {

	private int page = 1;

	private int size;

	private List<T> content;

	private long total;

	private int totalPages;

	public ResponsePage() {

	}

	/**
	 * for PageHelper
	 * 
	 * @param list
	 */
	public ResponsePage(List<T> list) {
		setContent(list);
		Pagination page = PageHelper.getPagination();
		setSize(page.getSize());
		setPage(page.getCurrent());
		setTotalPages(page.getPages());
		setTotal(page.getTotal());
		PageHelper.remove();
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

}
