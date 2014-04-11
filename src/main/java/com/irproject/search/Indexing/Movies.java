package com.irproject.search.Indexing;

public class Movies{

	/** Creates a new instance of Accommodation */
	public Movies() {
	}

	/** Creates a new instance of Accommodation */
	public Movies(
			String title,
			String actors,
			String actress,String director, String language, String genre, String productioncomp, String rating, String Plots, String locations) {

		this.title = title;
		this.actors = actors;
		this.actress = actress;
		this.director=director;
		this.language=language;
		this.genre=genre;
		this.productioncomp=productioncomp;
		this.rating=rating;
		this.plots=Plots;
		this.locations=locations;
	}

	/**
	 * Holds value of property title.
	 */

	private String director;
	private String rating;
	private String language;
	private String genre;
	private String productioncomp;
	private String plots;
	private String locations;
	/**
	 * Getter for property actress.
	 * @return Value of property actress.
	 */
	public String getdirector() {
		return this.director;
	}
	public String getlocations() {
		return this.locations;
	}
	public String getPlots() {
		return this.plots;
	}
	public String getlanguage() {
		return this.language;
	}
	public String getgenre() {
		return this.genre;
	}
	public String getproductioncomp() {
		return this.productioncomp;
	}
	public String getrating() {
		return this.rating;
	}

	/**
	 * Setter for property actress.
	 * @param actress New value of property actress.
	 */
	public void setdirector(String director) {
		this.director = director;
	}
	public void setlocations(String locations) {
		this.locations = locations;
	}
	public void setlanguage(String language) {
		this.language = language;
	}
	public void setproductioncomp(String productioncomp) {
		this.productioncomp = productioncomp;
	}
	public void setgenre(String genre) {
		this.genre = genre;
	}
	public void setrating(String rating) {
		this.rating = rating;
	}
	public void setPlots(String plots) {
		this.plots = plots;
	}
	private String title;

	/**
	 * Getter for property title.
	 * @return Value of property title.
	 */
	public String gettitle() {
		return this.title;
	}

	/**
	 * Setter for property title.
	 * @param title New value of property title.
	 */
	public void settitle(String title) {
		this.title = title;
	}

	/**
	 * Holds value of property id.
	 */

	/**
	 * Holds value of property actors.
	 */
	private String actors;

	/**
	 * Getter for property details.
	 * @return Value of property details.
	 */
	public String getactors() {
		return this.actors;
	}

	/**
	 * Setter for property details.
	 * @param details New value of property details.
	 */
	public void setactors(String actors) {
		this.actors = actors;
	}

	/**
	 * Holds value of property actress.
	 */
	private String actress;

	/**
	 * Getter for property actress.
	 * @return Value of property actress.
	 */
	public String getactress() {
		return this.actress;
	}

	/**
	 * Setter for property actress.
	 * @param actress New value of property actress.
	 */
	public void setactress(String actress) {
		this.actress = actress;
	}

	public String toString() {
		return "Movies "
				+ gettitle()
				+": "
				+ getactors()
				+","
				+ getactress()+","+getdirector()+","+getlanguage()+","+getgenre()+","+getproductioncomp()+","+getrating()+","+getPlots()+","+getlocations();
	}
}
