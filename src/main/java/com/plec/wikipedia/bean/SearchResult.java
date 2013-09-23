package com.plec.wikipedia.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class SearchResult {
	@Field
	public String title;
	@Field
	public String links;
	
	public List<String> highlight = new ArrayList<String>();
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLinks() {
		return links;
	}
	public void setLinks(String links) {
		this.links = links;
	}
	public List<String> getHighlight() {
		return highlight;
	}
	public void setHighlight(List<String> highlight) {
		this.highlight = highlight;
	}


}

