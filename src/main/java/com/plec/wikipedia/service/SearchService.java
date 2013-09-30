package com.plec.wikipedia.service;

import com.plec.wikipedia.SearchException;
import com.plec.wikipedia.bean.SearchResults;
import com.plec.wikipedia.dao.SearchESDao;
import com.plec.wikipedia.dao.SearchSolRDao;

public class SearchService {
	//spring injection
	private SearchSolRDao searchSolRDao;
	private SearchESDao searchESDao;
	
	private static final String ES_SEARCH_ENGINE = "ES";
	/**
	 * Search method
	 * @param query the query string
	 * @param searchEngine the name of the search engine ES : elastic search otherwise Solr
	 * @return an object SearchResult containing a list of results and some info such as time request and total number of matchs
	 * @throws SearchException exception from the search engine
	 */
	public SearchResults search(String query, String searchEngine) throws SearchException {
		if (ES_SEARCH_ENGINE.equalsIgnoreCase(searchEngine) ) {
			return searchESDao.search(query);
		}
		return searchSolRDao.search(query);

	}
	/**
	 * Setter method for property bean searchSolRDao Spring IoC
	 * @param searchSolRDao the bean searchSolRDao : solr search engine
	 */
	public void setSearchSolRDao(SearchSolRDao searchSolRDao) {
		this.searchSolRDao = searchSolRDao;
	}

	/**
	 * Setter method for property bean searchESDao Spring IoC
	 * @param searchESDao the bean searchESDao : elastic search search engine
	 */
	public void setSearchESDao(SearchESDao searchESDao) {
		this.searchESDao = searchESDao;
	}
}
