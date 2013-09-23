package com.plec.wikipedia.service;

import com.plec.wikipedia.bean.SearchResults;
import com.plec.wikipedia.dao.SearchESDao;
import com.plec.wikipedia.dao.SearchSolRDao;

public class SearchService {
	private SearchSolRDao searchSolRDao;
	private SearchESDao searchESDao;
	
	public SearchResults search(String query, int pageSize) {
		//return searchSolRDao.search(query, pageSize);
		return searchESDao.search(query, pageSize);
	}

	public void setSearchSolRDao(SearchSolRDao searchSolRDao) {
		this.searchSolRDao = searchSolRDao;
	}

	public void setSearchESDao(SearchESDao searchESDao) {
		this.searchESDao = searchESDao;
	}
}
