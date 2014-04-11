/**
 * 
 */
package com.irproject.search.dao;

import java.util.List;

import com.irproject.search.bean.RecommenderBean;

/**
 * @author JANANI
 *
 */
public interface RecommendationInter {
	
	List<RecommenderBean> getMoviesFromMoviesCluster(String movieId);
	List<RecommenderBean> getMoviesFromUserCluster(String userId);
	String getMovieId(String movieTitle);
	String getMovieTitle(String movieTitle);

}
