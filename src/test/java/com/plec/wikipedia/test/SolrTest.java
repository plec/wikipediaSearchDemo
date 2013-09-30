package com.plec.wikipedia.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.plec.wikipedia.bean.SearchResults;
import com.plec.wikipedia.dao.SearchSolRDao;

public class SolrTest {

	@Test
	public void testAccessSolr() {
		SearchSolRDao dao = new SearchSolRDao();
		dao.setMaxResults(10);
		dao.setSolrUrl("http://localhost:8983/solr/wikipedia");
		dao.init();
		assertTrue("connection to solr success", true);
	}

	@Test
	public void testFailedAccessSolr() {
		try {
			SearchSolRDao dao = new SearchSolRDao();
			dao.setMaxResults(10);
			dao.setSolrUrl("http://localhost:8883/solr/wikipedia");
			dao.init();
			dao.search("rome");
			assertTrue("connection to solr success", true);
		} catch (Exception e) {
			assertTrue("success fails to access to wrong sereveur", true);
		}
	}

	@Test
	public void testSearchRome() {
		try {
			SearchSolRDao dao = new SearchSolRDao();
			dao.setMaxResults(10);
			dao.setSolrUrl("http://localhost:8983/solr/wikipedia");
			dao.init();
			SearchResults result = dao.search("rome");
			assertTrue(result != null);
			assertTrue(result.getResultList() != null);
			assertTrue(result.getResultList().size() > 0);
			assertTrue(result.getResultList().size() <= 10);
		} catch (Exception e) {
			fail("Exception occurs " + e.getMessage());
		}
	}
	@Test
	public void testSearchAll() {
		try {
			SearchSolRDao dao = new SearchSolRDao();
			dao.setMaxResults(10);
			dao.setSolrUrl("http://localhost:8983/solr/wikipedia");
			dao.init();
			SearchResults result = dao.search("*");
			assertTrue(result != null);
			assertTrue(result.getResultList() != null);
			assertTrue(result.getResultList().size() > 0);
			assertTrue(result.getResultList().size() <= 10);
		} catch (Exception e) {
			fail("Exception occurs " + e.getMessage());
		}
	}

}
