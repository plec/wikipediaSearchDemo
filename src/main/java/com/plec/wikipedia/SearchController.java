package com.plec.wikipedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * Handles requests for the application search page.
 */
@Controller
public class SearchController {

	private static final Logger LOGGER = Logger
			.getLogger(SearchController.class);
	@Autowired
	private SearchService searchService;

	private int pageSize = 50;

	/**
	 * Page call with HTTP GET method : return the search page without results
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String initSearch(Locale locale, Model model) {
		model.addAttribute("searchResult", new ArrayList<SearchResult>());
		return "results";
	}
	/**
	 * Page call with HTTP POST method : return the search page with results
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@RequestParam("query") String query, @RequestParam("searchEngine") String searchEngine, Locale locale, Model model) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(" recherche de " + query);
		}
		if (query.length() == 0) {
			query= "*";
		}
		Pages pages = new Pages();
		try {
			
			SearchResults searchResults = searchService.search(query, searchEngine);
			if (searchResults.getTotalResults() < 1) {
				generateDummyResults(query, model, "<i>Aucun résultat</i>", searchResults.getRequestTime(), searchEngine);
			} else {
				pages.setTotalPages(searchResults.getTotalResults());
				pages.setPage(generatePagination(searchResults.getTotalResults()));
				model.addAttribute("searchResult", searchResults.getResultList());
				model.addAttribute("requestTime", searchResults.getRequestTime());
				model.addAttribute("engine", searchEngine);
				model.addAttribute("totalResults", searchResults.getTotalResults());
				model.addAttribute("searchQuery", query);
				model.addAttribute("pages", pages);
			}
		} catch (SearchException se) {
			LOGGER.error(se.getMessage());
			generateDummyResults(query, model, "<i>Une erreur s'est produite</i>", -1,searchEngine);
		}
		return "results";
	}
	/**
	 * Generate dummy results in case of no result or if error occurs
	 * @param query the query searched
	 * @param model the MVC model to put data results
	 * @param message the message to display
	 * @param requestTime the request time if known
	 */
	private void generateDummyResults(String query, Model model, String message, long requestTime, String searchEngine) {
		SearchResult sr = new SearchResult();
		sr.setHighlight(new ArrayList<String>());
		sr.getHighlight().add(" ");
		sr.setLinks("#");
		sr.setTitle(message);
		List<SearchResult> dummyResults = new ArrayList<SearchResult>();
		dummyResults.add(sr);
		Pages pages = new Pages();
		pages.setPage(new ArrayList<String>());
		pages.getPage().add(" ");
		model.addAttribute("searchResult", dummyResults);
		model.addAttribute("requestTime", requestTime);
		model.addAttribute("totalResults", 0);
		model.addAttribute("engine", searchEngine);
		model.addAttribute("searchQuery", query);
		model.addAttribute("pages",pages);
	}
	/**
	 * Generate the dummy pagination
	 * @param totalResult total number of results 
	 * @return a list of URLs to access to other pages
	 */
	private List<String> generatePagination(long totalResult) {
		List<String> pagesUrl = new ArrayList<String>();
		long totalPage = (totalResult / pageSize) +1;
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
		return pagesUrl;
	}
	/**
	 * Setter method for property bean searchService Spring IoC
	 * @param searchService the bean searchService
	 */
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	/**
	 * Setter method for property pageSize Spring IoC
	 * 
	 * @param pageSize the number of result per page
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}

