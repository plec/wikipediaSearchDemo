package com.plec.wikipedia.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.plec.wikipedia.bean.SearchResults;
import com.plec.wikipedia.dao.SearchESDao;

public class ESTest {

	@Test
	public void testAccessES() {
		SearchESDao dao = new SearchESDao();
		dao.setEsHost("localhost");
		dao.setEsPort(9300);
		dao.init();
		assertTrue("connection to es success", true);
	}

	@Test
	public void testFailedAccessES() {
		try {
			SearchESDao dao = new SearchESDao();
			dao.setEsHost("localhost");
			dao.setEsPort(9000);
			dao.init();
			SearchResults result = dao.search("rome");
			result.getResultList().size();
		} catch (Exception e) {
			assertTrue("success fails to access to wrong sereveur", true);
		}
	}
	@Test
	public void testSearchRome() {
		try {
			SearchESDao dao = new SearchESDao();
			dao.setEsHost("localhost");
			dao.setEsPort(9300);
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
}
