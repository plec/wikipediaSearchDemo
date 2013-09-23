package com.plec.wikipedia.bean;

import java.util.List;

public class Pages {
	private long totalPages;
	private List<String> page;
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	public List<String> getPage() {
		return page;
	}
	public void setPage(List<String> page) {
		this.page = page;
	}
}
