package com.plec.wikipedia.service;

import java.util.List;

import com.plec.wikipedia.bean.SearchResult;
import com.plec.wikipedia.dao.SearchSolRDao;

public class SearchService {
	private SearchSolRDao searchSolRDao;
	
	public List<SearchResult> search(String query) {
		return searchSolRDao.search(query);
	}

	public void setSearchSolRDao(SearchSolRDao searchSolRDao) {
		this.searchSolRDao = searchSolRDao;
	}
}
