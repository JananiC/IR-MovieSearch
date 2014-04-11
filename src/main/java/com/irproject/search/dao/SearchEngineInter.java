/**
 * 
 */
package com.irproject.search.dao;

import java.util.ArrayList;

import com.irproject.search.bean.MovieResBean;

/**
 * @author JANANI
 *
 */
public interface SearchEngineInter {
	
	ArrayList<String> fetchIndices();
	 boolean fetchMovieList(String userId);
	 String getImdbPost(String movieTitle,String movId);

}
