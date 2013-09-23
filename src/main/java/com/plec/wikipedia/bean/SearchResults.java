package com.plec.wikipedia.bean;

import java.util.List;

public class SearchResults {
	private List<SearchResult> resultList;
	private long totalResults;
	private long requestTime;
	public List<SearchResult> getResultList() {
		return resultList;
	}
	public void setResultList(List<SearchResult> resultList) {
		this.resultList = resultList;
	}
	public long getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(long totalResults) {
		this.totalResults = totalResults;
	}
	public long getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(long requestTime) {
		this.requestTime = requestTime;
	}
}
