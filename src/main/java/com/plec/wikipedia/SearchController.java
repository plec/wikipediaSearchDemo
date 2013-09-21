package com.plec.wikipedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.plec.wikipedia.bean.SearchResult;
import com.plec.wikipedia.service.SearchService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SearchController {

	private static final Logger logger = Logger
			.getLogger(SearchController.class);
	@Autowired
	private SearchService searchService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/initSearch", method = RequestMethod.GET)
	public String result(Locale locale, Model model) {
		logger.info(" recherche ");

//		// get the query string
//		String query = "rome";
//		// List<SearchResult> resultats = solrDao.search(query);
//		List<SearchResult> resultats = new ArrayList<SearchResult>();
//		SearchResult r = new SearchResult();
//		r.setTitle("title1");
//		List<String> links = new ArrayList<String>();
//		links.add("http://fr.wikipedia.org/wiki?curid=3");
//		r.setLinks(links);
//		resultats.add(r);
//
//		r = new SearchResult();
//		r.setTitle("title2");
//		List<String> link2s = new ArrayList<String>();
//		link2s.add("http://fr.wikipedia.org/wiki?curid=7");
//		r.setLinks(link2s);
//		resultats.add(r);
//
		model.addAttribute("searchResult", new ArrayList<SearchResult>());

		return "results";
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@RequestParam("query") String query, Locale locale, Model model) {
		logger.info(" recherche de " + query);
		List<SearchResult> resultats = searchService.search(query);
		model.addAttribute("searchResult", resultats);
		return "results";
	}
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

}

