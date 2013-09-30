package com.plec.wikipedia.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import com.plec.wikipedia.SearchException;
import com.plec.wikipedia.bean.SearchResult;
import com.plec.wikipedia.bean.SearchResults;

public class SearchSolRDao implements SearchDao {
	//LOGGER
	private static final Logger LOGGER = Logger.getLogger(SearchSolRDao.class);

	//internal constants
	private static final String HIGHLIGHT_FIELD_SOLR_CONFIG = "hl.fl";

	//Spring properties
	private String solrUrl;
	private int maxResults;

	private SolrServer solr = null;
	/**
	 * Init method to initialize the solr HttpSolrServer object
	 */
	public void init() {
		solr = new HttpSolrServer(solrUrl);		
	}
	/**
	 * search method
	 * @param query the query string to search
	 * @return a searchResults object containing a list of result and some info such as time request and total number of results
	 * @throws SearchException if exception occurs
	 */
	public SearchResults search(String query)  throws SearchException {
		try {
			//Process Query to prepend OR between each search words
			String processedQuery = processInputQuery(query);
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("SolR query : "+processedQuery);
			}
			//create the solr query
			SolrQuery solRQuery = new SolrQuery();
			solRQuery.setQuery(processedQuery);
			solRQuery.setRows(maxResults);
			boolean useHighLight = (!query.equals("*"));
			//if not '*' search then highlight
			if (useHighLight) {
				solRQuery.setHighlight(true).setHighlightSnippets(3); //set other params as needed
				solRQuery.setParam(HIGHLIGHT_FIELD_SOLR_CONFIG, SearchConstants.FIELD_CONTENT);
			}

			QueryResponse rsp = solr.query( solRQuery );
			
			SearchResults results = new SearchResults();
			//results.setResultList(rsp.getBeans(SearchResult.class));
			List<SearchResult> searchResultList = new ArrayList<SearchResult>();
			for (SolrDocument resultDoc : rsp.getResults()) {
				SearchResult searchResult = new SearchResult();
		    	searchResult.setHighlight(new ArrayList<String>());
				searchResult.setTitle((String) resultDoc.getFieldValue(SearchConstants.FIELD_TITLE));
				searchResult.setLinks((String) resultDoc.getFieldValue(SearchConstants.FIELD_LINKS));
				if(useHighLight) {
					String id = (String) resultDoc.getFieldValue(SearchConstants.FIELD_ID); //id is the uniqueKey field
			    	if (rsp.getHighlighting().get(id) != null) {
			    		List<String> highlightSnippets = rsp.getHighlighting().get(id).get(SearchConstants.FIELD_CONTENT);
			    		if (highlightSnippets != null) {
			    			searchResult.getHighlight().addAll(highlightSnippets);
			    		}
					}
				}
		    	searchResultList.add(searchResult);		    	
			}
			results.setResultList(searchResultList);
			results.setTotalResults(rsp.getResults().getNumFound());
			results.setRequestTime(rsp.getElapsedTime());
				
			
			return results;
		} catch (Exception e) {
			LOGGER.error("Error while querying solr for query : " + query, e);
			throw new SearchException("Error while querying solr for query : " + query, e);
		}
	}
	/**
	 * Method to process Query to prepend OR between each search words
	 * @param query the raw input query
	 * @return the input query with 'OR' between each word
	 */
	private String processInputQuery(String query) {
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
		return newQuery.toString();
	}

	/**
	 * Setter method for property solrUrl Spring IoC
	 * @param solrUrl the url of the solr search engine
	 */
	public void setSolrUrl(String solrUrl) {
		this.solrUrl = solrUrl;
	}
	/**
	 * Setter method for property maxResults Spring IoC
	 * @param maxResults the max number of result for a query
	 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

}