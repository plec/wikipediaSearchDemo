package com.plec.wikipedia.dao;

import com.plec.wikipedia.bean.SearchResults;

public interface SearchDao {
	public SearchResults search(String query, int pageSize);
}
