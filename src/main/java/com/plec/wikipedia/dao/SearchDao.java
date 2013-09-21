package com.plec.wikipedia.dao;

import java.util.List;

import com.plec.wikipedia.bean.SearchResult;

public interface SearchDao {
	public List<SearchResult> search(String query);
}
