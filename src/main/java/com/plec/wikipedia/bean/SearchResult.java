package com.plec.wikipedia.bean;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class SearchResult {
	@Field
	public String title;
	@Field
	public List<String> links;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getLinks() {
		return links;
	}
	public void setLinks(List<String> links) {
		this.links = links;
	}

}

