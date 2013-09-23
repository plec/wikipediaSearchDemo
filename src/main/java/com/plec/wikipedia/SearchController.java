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

import com.plec.wikipedia.bean.Pages;
import com.plec.wikipedia.bean.SearchResult;
import com.plec.wikipedia.bean.SearchResults;
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
	private static final int PAGE_SIZE = 20;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/initSearch", method = RequestMethod.GET)
	public String result(Locale locale, Model model) {
		logger.info(" recherche ");
		model.addAttribute("searchResult", new ArrayList<SearchResult>());
		return "results";
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@RequestParam("query") String query, Locale locale, Model model) {
		logger.info(" recherche de " + query);
		if (query.length() == 0) {
			query= "*";
		}
		Pages pages = new Pages();
		SearchResults searchResults = searchService.search(query, PAGE_SIZE);
		pages.setTotalPages(searchResults.getTotalResults());
		List<String> pagesUrl = new ArrayList<String>();
		long totalPage = (searchResults.getTotalResults() / PAGE_SIZE) +1;
		if (totalPage > 6) {
			pagesUrl.add("page - 1");
			pagesUrl.add("page - 2");
			pagesUrl.add("page - 3");
			pagesUrl.add("...");
			pagesUrl.add("page - "+ (totalPage-2));
			pagesUrl.add("page - "+ (totalPage-1));
			pagesUrl.add("page - "+ totalPage);
		} else {
			for (long i =0; i< totalPage; i++) {
				pagesUrl.add("page - " +i);
			}
		}
		pages.setPage(pagesUrl);

		model.addAttribute("searchResult", searchResults.getResultList());
		model.addAttribute("requestTime", searchResults.getRequestTime());
		model.addAttribute("totalResults", searchResults.getTotalResults());
		model.addAttribute("searchQuery", query);
		model.addAttribute("pages", pages);
		return "results";
	}
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

}

