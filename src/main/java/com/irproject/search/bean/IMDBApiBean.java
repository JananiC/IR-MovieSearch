package com.irproject.search.bean;

import java.io.Serializable;

public class IMDBApiBean implements Serializable{
	/**
	 * 
	 */
	
	private String movieId;
	private String movieTitle;
	private String docId;
	private String year;
	private String plots;
	private String rating;
	private String score;
	
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPlots() {
		return plots;
	}
	public void setPlots(String plots) {
		this.plots = plots;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	private static final long serialVersionUID = 1L;
	private String imdbid;
	private String imdburl;
	public String getImdbid() {
		return imdbid;
	}
	public void setImdbid(String imdbid) {
		this.imdbid = imdbid;
	}
	public String getImdburl() {
		return imdburl;
	}
	public void setImdburl(String imdburl) {
		this.imdburl = imdburl;
	}
	public String getImdbgenres() {
		return imdbgenres;
	}
	public void setImdbgenres(String imdbgenres) {
		this.imdbgenres = imdbgenres;
	}
	public String getImdblanguages() {
		return imdblanguages;
	}
	public void setImdblanguages(String imdblanguages) {
		this.imdblanguages = imdblanguages;
	}
	public String getImdbcountry() {
		return imdbcountry;
	}
	public void setImdbcountry(String imdbcountry) {
		this.imdbcountry = imdbcountry;
	}
	public String getImdbRating() {
		return imdbRating;
	}
	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}
	public String getImdbrunTime() {
		return imdbrunTime;
	}
	public void setImdbrunTime(String imdbrunTime) {
		this.imdbrunTime = imdbrunTime;
	}
	public String getImdbYear() {
		return imdbYear;
	}
	public void setImdbYear(String imdbYear) {
		this.imdbYear = imdbYear;
	}
	private String imdbgenres;
	
	private String posterUrl;
	public String getPosterUrl() {
		return posterUrl;
	}
	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}
	private String imdblanguages;
	private String imdbcountry;
	private String imdbRating;
	private String imdbrunTime;
	private String imdbYear; 
	private String imdbTitle;
	public String getImdbTitle() {
		return imdbTitle;
	}
	public void setImdbTitle(String imdbTitle) {
		this.imdbTitle = imdbTitle;
	}
	

	private String locations;

	public String getLocations() {
		return locations;
	}
	public void setLocations(String locations) {
		this.locations = locations;
	}
}
