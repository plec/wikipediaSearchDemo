package com.plec.wikipedia.dao;

import com.plec.wikipedia.SearchException;
import com.plec.wikipedia.bean.SearchResults;

public interface SearchDao {
	/**
	 * Search DAO query
	 * @param query the query string to search
	 * @return a searchResults object containing a list of result and some info such as time request and total number of results
	 * @throws SearchException if exception occurs while searching
	 */
	public SearchResults search(String query) throws SearchException;
}
