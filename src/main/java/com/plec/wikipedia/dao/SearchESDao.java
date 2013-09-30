package com.plec.wikipedia.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;

import com.plec.wikipedia.SearchException;
import com.plec.wikipedia.bean.SearchResult;
import com.plec.wikipedia.bean.SearchResults;

public class SearchESDao implements SearchDao {
	// LOGGER
	private static final Logger LOGGER = Logger.getLogger(SearchESDao.class);

	// Spring IoC
	private int maxResults;
	private int esPort;
	private String esHost;
	private String esIndexName;
	
	//Elastic Search Client, initialized by init method
	Client clientES = null;

	/**
	 * init method to initialize clientES object
	 */
	public void init() {
		LOGGER.info("connecting ES server ...");
		clientES = new TransportClient()
		.addTransportAddress(new InetSocketTransportAddress(
				esHost, esPort));
		LOGGER.info("connecting ES server ... done");

	}

	/**
	 * search method
	 * @param query the query string to search
	 * @return a searchResults object containing a list of result and some info such as time request and total number of results
	 */
	public SearchResults search(String query) throws SearchException {
		try {
			boolean matchAllQuery = (query.equals("*"));
			QueryBuilder qb = null;
			if (matchAllQuery) {
				qb = QueryBuilders.matchAllQuery();
			} else if (StringUtils.contains(query,  "*") || StringUtils.contains(query,  "?")) {
				qb = new WildcardQueryBuilder(SearchConstants.FIELD_CONTENT, query);
			} else {
				qb = QueryBuilders.matchQuery(SearchConstants.FIELD_CONTENT, query);
			}

			SearchRequestBuilder searchRequestBuilder = clientES.prepareSearch(esIndexName)
					//.setTypes("articles")
			        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
			        .setQuery(qb)             // Query
			        .setFrom(0).setSize(maxResults).setExplain(true);
			if (!matchAllQuery) {
				searchRequestBuilder.addHighlightedField(SearchConstants.FIELD_CONTENT, 200, 3);
				
			}
			SearchResponse response = searchRequestBuilder.execute()
			        .actionGet();
			SearchResults results = new SearchResults();
			results.setResultList(new ArrayList<SearchResult>());
			results.setRequestTime(response.getTookInMillis());
			results.setTotalResults(response.getHits().getTotalHits());
			for (int i =0; i< response.getHits().getHits().length; i++) {
				SearchResult searchResult = new SearchResult();
				SearchHit hit = response.getHits().getHits()[i];
				Map<String, Object> rsp = hit.sourceAsMap();
				searchResult.setHighlight(new ArrayList<String>());
				searchResult.setTitle((String) rsp.get(SearchConstants.FIELD_TITLE));
				searchResult.setLinks((String) rsp.get(SearchConstants.FIELD_LINKS));
				if(!matchAllQuery) {
					String highligh = hit.getHighlightFields().get(SearchConstants.FIELD_CONTENT).getFragments()[0].toString();
					searchResult.getHighlight().add(highligh);
				}
				results.getResultList().add(searchResult);
			}
			
			return results;
		} catch (Exception sse) {
			LOGGER.error("Error while querying elastic search for query : " + query, sse);
			throw new SearchException("Error while querying solr for query : " + query, sse);
		}
	}
	/**
	 * Setter method for property maxResults Spring IoC
	 * @param maxResults the max number of result for a query
	 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}


	/**
	 * Setter method for property esPort Spring IoC
	 * @param esPort the port number of the es search engine
	 */
	public void setEsPort(int esPort) {
		this.esPort = esPort;
	}

	/**
	 * Setter method for property esHost Spring IoC
	 * @param esHost the host name of the es search engine
	 */
	public void setEsHost(String esHost) {
		this.esHost = esHost;
	}
	/**
	 * Setter method for property esIndexName Spring IoC
	 * @param esHost the index name name of the es search engine
	 */
	public void setEsIndexName(String esIndexName) {
		this.esIndexName = esIndexName;
	}

}