/**
 * 
 */
package com.irproject.search.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.irproject.search.bean.RecommenderBean;
import com.irproject.search.dao.DataSource;
import com.irproject.search.dao.RecommendationInter;

/**
 * @author JANANI
 *
 */
public class RecommendationImpl implements RecommendationInter {
	
	/** The result set. */
    private ResultSet resultSet;

    /** The pstmt. */
    private PreparedStatement pstmt;
    
    private RecommenderBean recommenderBean;

	/* (non-Javadoc)
	 * @see com.irproject.search.dao.RecommendationInter#getMoviesFromMoviesCluster(java.lang.String)
	 */
	public RecommenderBean getRecommenderBean() {
		return recommenderBean;
	}

	public void setRecommenderBean(RecommenderBean recommenderBean) {
		this.recommenderBean = recommenderBean;
	}

	@Override
	public List<RecommenderBean> getMoviesFromMoviesCluster(String movieId) {
		System.out.println("inside getMoviesFromMoviesCluster");
        DataSource dataSource = new DataSource();
        Connection connection = null;
        List<RecommenderBean> resBean = new ArrayList<RecommenderBean>();
        try {
            connection = (Connection) ((JdbcTemplate) dataSource
                    .getBeanFactory().getBean("tbsJdbcTemplate"))
                    .getDataSource().getConnection();

            pstmt = connection.prepareStatement("select REC_MOVIE_ID,rating from (select REC_MOVIE_ID, i_mov.rating from " +
            		"(select clusmov.movie_id as REC_MOVIE_ID from DM_MOVIE_CLUSTERS MovClus inner join " +
            		"dm_clusters_movies ClusMov on movclus.movie_cluster_id = clusmov.movie_cluster_id where movclus.movie_id=?) " +
            		"rec_list inner join i_movieratings i_mov on i_mov.movieid = rec_list.REC_MOVIE_ID  order by i_mov.rating desc) where rownum < 6");
           System.out.println("MovieIdSSSSSS==="+movieId);
            pstmt.setInt(1, Integer.parseInt(movieId));
            resultSet = pstmt.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) { 
                	RecommenderBean bean = new RecommenderBean();
                	System.out.println("Inside setting recommender bean");
                	    	int rec_mov_Id = resultSet.getInt("REC_MOVIE_ID");
                	    	int rec_mov_rat = resultSet.getInt("rating");
                	    	bean.setRecMovieId(String.valueOf(rec_mov_Id));
                	    	bean.setRecMovRat(String.valueOf(rec_mov_rat));
                	    	System.out.println("Rec MovieId  "+bean.getRecMovieId());
                	    	System.out.println("Rec MovieRat "+bean.getRecMovRat());
                	    	resBean.add(bean);
                }
                
            }
        } catch (SQLException ex) {
           System.out.println("An Exception has getMoviesFromMoviesCluster" +ex);
        } finally {
            try {
                if (resultSet != null) {
                        resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception excep) {
               System.out.println("Exception while closing"
                        + " conn - getMoviesFromMoviesCluster! " + excep);
            }
          }

      
        return resBean;
    
		
	}

	/* (non-Javadoc)
	 * @see com.irproject.search.dao.RecommendationInter#getMoviesFromUserCluster(java.lang.String)
	 */
	@Override
	public List<RecommenderBean> getMoviesFromUserCluster(String userId) {
		System.out.println("inside getMoviesFromUserCluster");
        DataSource dataSource = new DataSource();
        Connection connection = null;
        List<RecommenderBean> resBean = new ArrayList<RecommenderBean>();
        try {
            connection = (Connection) ((JdbcTemplate) dataSource
                    .getBeanFactory().getBean("tbsJdbcTemplate"))
                    .getDataSource().getConnection();

            pstmt = connection.prepareStatement("select REC_MOVIE_ID,rating from (select Clussuer.movieid as REC_MOVIE_ID,Clussuer.rating from DM_USER_CLUSTERS userClus inner join " +
            		"dm_clusters_user Clussuer on userClus.USER_CLUSTERS = Clussuer.CLUSTERUSER where userClus.USER_ID=? order by Clussuer.rating desc) where rownum < 6");
           System.out.println("USER IDDDDD==="+userId);
            pstmt.setInt(1, Integer.parseInt(userId));
            resultSet = pstmt.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) { 
                	RecommenderBean bean = new RecommenderBean();
                	System.out.println("Inside setting recommender bean");
                	    	int rec_mov_Id = resultSet.getInt("REC_MOVIE_ID");
                	    	int rec_mov_rat = resultSet.getInt("rating");
                	    	bean.setRecMovieId(String.valueOf(rec_mov_Id));
                	    	bean.setRecMovRat(String.valueOf(rec_mov_rat));
                	    	System.out.println("Rec MovieId  "+bean.getRecMovieId());
                	    	System.out.println("Rec MovieRat "+bean.getRecMovRat());
                	    	resBean.add(bean);
                }
                
            }
        } catch (SQLException ex) {
           System.out.println("An Exception has getMoviesFromUserCluster" +ex);
        } finally {
            try {
                if (resultSet != null) {
                        resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception excep) {
               System.out.println("Exception while closing"
                        + " conn - getMoviesFromUserCluster! " + excep);
            }
          }

      
        return resBean;
    
		
	}

	@Override
	public String getMovieId(String movieTitle) {
		System.out.println("Inside getMovieId");
        DataSource dataSource = new DataSource();
        Connection connection = null;
        String movieId =null;
        try {
            connection = (Connection) ((JdbcTemplate) dataSource
                    .getBeanFactory().getBean("tbsJdbcTemplate"))
                    .getDataSource().getConnection();
            System.out.println("movietitlelllllll----"+movieTitle);
            pstmt = connection.prepareStatement("select MOVIEID from I_MOVIES where TITLEMOVIELENS like ? AND ROWNUM=1");
            pstmt.setString(1, movieTitle+"%");
           
            resultSet = pstmt.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                	movieId = resultSet.getString("MOVIEID");
                	System.out.println("Movieid---"+movieId);
                }
                
            }
        } catch (SQLException ex) {
           System.out.println("An Exception has getMovieId" +ex);
        } finally {
            try {
                if (resultSet != null) {
                        resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception excep) {
               System.out.println("Exception while closing"
                        + " conn - getMovieId! " + excep);
            }
          }

      
        return movieId;
    }
	
	public String getMovieTitle(String movieId) {
        DataSource dataSource = new DataSource();
        Connection connection = null;
        String movieTitle =null;
        try {
            connection = (Connection) ((JdbcTemplate) dataSource
                    .getBeanFactory().getBean("tbsJdbcTemplate"))
                    .getDataSource().getConnection();

            pstmt = connection.prepareStatement("select MOVIE_TITLE from MOVIE_IMAGE_URL where MOVIEID=? AND ROWNUM=1");
            pstmt.setInt(1, Integer.parseInt(movieId));
           
            resultSet = pstmt.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                	movieTitle = resultSet.getString("MOVIE_TITLE");
                	System.out.println("MOVIE_TITLE in getMovieTitle---"+movieTitle);
                }
                
            }
        } catch (SQLException ex) {
           System.out.println("An Exception has getMovieTitle" +ex);
        } finally {
            try {
                if (resultSet != null) {
                        resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception excep) {
               System.out.println("Exception while closing"
                        + " conn - getMovieTitle! " + excep);
            }
          }

      
        return movieTitle;
    }

}
