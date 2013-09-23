package com.plec.wikipedia.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import com.plec.wikipedia.bean.SearchResult;
import com.plec.wikipedia.bean.SearchResults;

public class SearchSolRDao implements SearchDao {
	private static final String SOLR_SERVEUR_URL = "http://localhost:8983/solr/wikipedia";
	private SolrServer solr = null;
	private static final Logger LOGGER = Logger.getLogger(SearchSolRDao.class);

	public SearchSolRDao() {
		solr = new HttpSolrServer(SOLR_SERVEUR_URL);
	}

	
	public SearchResults search(String query, int pageSize) {
		try {
			SolrQuery solRQuery = new SolrQuery();
			String[] splitedQuery = query.split(" ");
			StringBuilder newQuery = new StringBuilder();
			int size = splitedQuery.length;
			newQuery.append("content:(");
			for (int i = 0; i< size; i++) {
				newQuery.append(splitedQuery[i]);
				if (size > 1 && i <(size-1)) {
					newQuery.append(" OR ");
				}
			}
			newQuery.append(")");
			solRQuery.setQuery( newQuery.toString());
			solRQuery.setRows(pageSize);
			LOGGER.info("SolR query : "+newQuery.toString());

			solRQuery.setHighlight(true).setHighlightSnippets(1); //set other params as needed
			solRQuery.setParam("hl.fl", "content");
			
			QueryResponse rsp = solr.query( solRQuery );
			
			SearchResults results = new SearchResults();
			//results.setResultList(rsp.getBeans(SearchResult.class));
			List<SearchResult> searchResultList = new ArrayList<SearchResult>();
			for (SolrDocument resultDoc : rsp.getResults()) {
				SearchResult searchResult = new SearchResult();
				searchResult.setTitle( (String) resultDoc.getFieldValue("title"));
				searchResult.setLinks( (String) resultDoc.getFieldValue("links"));
				searchResult.setTitle( (String) resultDoc.getFieldValue("title"));
			    String id = (String) resultDoc.getFieldValue("id"); //id is the uniqueKey field
		    	if (rsp.getHighlighting().get(id) != null) {
		    		List<String> highlightSnippets = rsp.getHighlighting().get(id).get("content");
			    	searchResult.setHighlight(highlightSnippets);
		    	}
		    	searchResultList.add(searchResult);		    	
			}
			results.setResultList(searchResultList);
			results.setTotalResults(rsp.getResults().getNumFound());
			results.setRequestTime(rsp.getElapsedTime());
				
			
			return results;
		} catch (SolrServerException sse) {
			LOGGER.error("Error while querying solr for query : " + query, sse);
			return null;
		}
	}
	public static void main(String[] args) {
		SearchSolRDao dao = new SearchSolRDao();
		SearchResults result = dao.search("rome", 10);
		result.getResultList().size();
	}
}

