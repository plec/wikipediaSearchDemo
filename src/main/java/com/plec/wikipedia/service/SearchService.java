package com.plec.wikipedia.service;

import com.plec.wikipedia.bean.SearchResults;
import com.plec.wikipedia.dao.SearchSolRDao;

public class SearchService {
	private SearchSolRDao searchSolRDao;
	
	public SearchResults search(String query) {
		return searchSolRDao.search(query);
	}

	public void setSearchSolRDao(SearchSolRDao searchSolRDao) {
		this.searchSolRDao = searchSolRDao;
	}
}
