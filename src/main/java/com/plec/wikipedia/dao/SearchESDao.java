package com.plec.wikipedia.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.plec.wikipedia.bean.SearchResult;
import com.plec.wikipedia.bean.SearchResults;

public class SearchESDao implements SearchDao {
	private static final String ES_HOST = "localhost";
	private static final int ES_PORT = 9300;
	Client clientES = null;
	private static final Logger LOGGER = Logger.getLogger(SearchESDao.class);

	public SearchESDao() {
		LOGGER.info("connecting ES server ...");
		clientES = new TransportClient()
		.addTransportAddress(new InetSocketTransportAddress(
				ES_HOST, ES_PORT));
		LOGGER.info("connecting ES server ... done");

	}

	
	public SearchResults search(String query, int pageSize) {
		try {
			
			QueryBuilder qb = QueryBuilders.matchQuery("content", query);
			
			SearchResponse response = clientES.prepareSearch("wikipedia")
			        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
			        .setQuery(qb)             // Query
			        .setFrom(0).setSize(50).setExplain(true)
			        .execute()
			        .actionGet();
			SearchResults results = new SearchResults();
			results.setResultList(new ArrayList<SearchResult>());
			results.setRequestTime(response.getTookInMillis());
			results.setTotalResults(response.getHits().getTotalHits());
			for (int i =0; i< response.getHits().getHits().length; i++) {
				SearchResult searchResult = new SearchResult();
				SearchHit hit = response.getHits().getHits()[i];
				Map<String, Object> rsp = hit.sourceAsMap();
				//String highligh = hit.getHighlightFields().get("content").getFragments()[0].toString();
				searchResult.setTitle((String) rsp.get("title"));
				searchResult.setLinks((String) rsp.get("links"));
				searchResult.setHighlight(new ArrayList<String>());
				//searchResult.getHighlight().add(highligh);
				results.getResultList().add(searchResult);
			}
			
			return results;
		} catch (Exception sse) {
			LOGGER.error("Error while querying elastic search for query : " + query, sse);
			return null;
		}
	}
	public static void main(String[] args) {
		SearchESDao dao = new SearchESDao();
		SearchResults result = dao.search("rome", 10);
		result.getResultList().size();
	}
}

