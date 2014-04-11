/**
 * 
 */
package com.irproject.search.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * @author JANANI
 *
 */
public class SearchTypes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map searchType;

	public Map getSearchType() {
		return searchType;
	}

	public void setSearchType(Map searchType) {
		this.searchType = searchType;
	}


}
