/**
 * 
 */
package com.irproject.search.bean;

import java.io.Serializable;

/**
 * @author JANANI
 *
 */
public class SearchBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String searchId;
	private String searchType;
	public String getSearchId() {
		return searchId;
	}
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	private String spellcheck;
	public String getSpellcheck() {
		return spellcheck;
	}
	public void setSpellcheck(String spellcheck) {
		this.spellcheck = spellcheck;
	}
}
