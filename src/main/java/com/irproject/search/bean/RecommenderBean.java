/**
 * 
 */
package com.irproject.search.bean;

import java.io.Serializable;

/**
 * @author JANANI
 *
 */
public class RecommenderBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recMovieId;
	private String recMovRat;
	private String imageUrl;
	private String imdbUrl;
	public String getImdbUrl() {
		return imdbUrl;
	}
	public void setImdbUrl(String imdbUrl) {
		this.imdbUrl = imdbUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getRecMovRat() {
		return recMovRat;
	}
	public void setRecMovRat(String recMovRat) {
		this.recMovRat = recMovRat;
	}
	public String getRecMovieId() {
		return recMovieId;
	}
	public void setRecMovieId(String recMovieId) {
		this.recMovieId = recMovieId;
	}
	

}
