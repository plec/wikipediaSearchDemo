package com.plec.wikipedia.dao;

import java.util.List; 

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.plec.wikipedia.bean.SearchResult;

public class SearchSolRDao implements SearchDao {
	private static final String SOLR_SERVEUR_URL = "http://localhost:8983/solr/wikipedia";
	private SolrServer solr = null;
	private static final Logger LOGGER = Logger.getLogger(SearchSolRDao.class);

	public SearchSolRDao() {
		solr = new HttpSolrServer(SOLR_SERVEUR_URL);
	}

	
	public List<SearchResult> search(String query) {
		try {
			SolrQuery solRQuery = new SolrQuery();
			solRQuery.setQuery( "content:\""+query+"\"");
			QueryResponse rsp = solr.query( solRQuery );
			List<SearchResult> beans = rsp.getBeans(SearchResult.class);
			return beans;
		} catch (SolrServerException sse) {
			LOGGER.error("Error while querying solr for query : " + query, sse);
			return null;
		}
	}
	public static void main(String[] args) {
		SearchSolRDao dao = new SearchSolRDao();
		List<SearchResult> result = dao.search("rome");
		result.size();
	}
}

