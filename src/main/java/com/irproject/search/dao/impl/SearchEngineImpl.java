/**
 * 
 */
package com.irproject.search.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.irproject.search.bean.MovieResBean;
import com.irproject.search.bean.NERBean;
import com.irproject.search.dao.DataSource;
import com.irproject.search.dao.SearchEngineInter;

/**
 * @author JANANI
 *
 */
public class SearchEngineImpl implements SearchEngineInter {

	
	/** The result set. */
    private ResultSet resultSet;

    /** The pstmt. */
    private PreparedStatement pstmt;
	/* (non-Javadoc)
	 * @see com.irproject.search.dao.SearchEngineInter#fetchIndices(com.irproject.search.bean.SearchBean)
	 */
	@Override
	public ArrayList<String> fetchIndices() {
        DataSource dataSource = new DataSource();
        Connection connection = null;
        ArrayList<String> indexList = new ArrayList<String>();
        try {
            connection = (Connection) ((JdbcTemplate) dataSource
                    .getBeanFactory().getBean("tbsJdbcTemplate"))
                    .getDataSource().getConnection();

            pstmt = connection.prepareStatement("select Autocomplete from ALLINFO");
           
           
            resultSet = pstmt.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                	String index = resultSet.getString("Autocomplete");
                	System.out.println(index);
                	indexList.add(index);
                }
                
            }
        } catch (SQLException ex) {
           System.out.println("An Exception has fetchIndices" +ex);
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
                        + " conn - fetchIndices! " + excep);
            }
          }

      
        return indexList;
    }
	
	public void fetchNERList(List<NERBean> nerlist, String query){

        DataSource dataSource = new DataSource();
        Connection connection = null;
        ArrayList<String> indexList = new ArrayList<String>();
        
           try {
            connection = (Connection) ((JdbcTemplate) dataSource
                    .getBeanFactory().getBean("tbsJdbcTemplate"))
                    .getDataSource().getConnection();
            
            
            Iterator iter = nerlist.iterator();
            
            
            while(iter.hasNext()){
            	NERBean nerBean = (NERBean)iter.next();
            	System.out.println(nerBean.getWord()+" "+nerBean.getNer());
            	String NER = nerBean.getNer();
            	
            	if(null!=NER && NER.equalsIgnoreCase("LOCATION")){
            		pstmt = connection.prepareStatement("select M.TITLEIMDB from C_MOVIEPRODUCTIONCOMPANIES C,I_MOVIES M WHERE C.MOVIEID = M.MOVIEID AND C.COMPANYNAME LIKE '?'");
            	}
            }

            
           
           
            resultSet = pstmt.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                	String index = resultSet.getString("TITLEIMDB");
                	System.out.println(index);
                	indexList.add(index);
                }
                
            }
        } catch (SQLException ex) {
           System.out.println("An Exception has fetchIndices" +ex);
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
                        + " conn - fetchIndices! " + excep);
            }
          }

      
        
    
	}
	
	@Override
	public  boolean fetchMovieList(String userId) {
        DataSource dataSource = new DataSource();
        Connection connection = null;
        
        boolean resBean = false;
    
        try {
            connection = (Connection) ((JdbcTemplate) dataSource
                    .getBeanFactory().getBean("tbsJdbcTemplate"))
                    .getDataSource().getConnection();

            pstmt = connection.prepareStatement("select count(1) as count from DM_USER_CLUSTERS where USER_ID =?");
           
            pstmt.setInt(1, Integer.parseInt(userId));
            resultSet = pstmt.executeQuery();
            if (resultSet != null) {
                if(resultSet.isBeforeFirst()){
                	resBean= true;
                }
                
            }
        } catch (SQLException ex) {
           System.out.println("An Exception has fetchMovieList" +ex);
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
                        + " conn - fetchMovieList! " + excep);
            }
          }

      
        return resBean;
    }

	@Override
	public String getImdbPost(String movieTitle,String movieId) {
        DataSource dataSource = new DataSource();
        Connection connection = null;
      String posterUrl = null;
        try {
            connection = (Connection) ((JdbcTemplate) dataSource
                    .getBeanFactory().getBean("tbsJdbcTemplate"))
                    .getDataSource().getConnection();

            pstmt = connection.prepareStatement("select IMAGE_URL from MOVIE_IMAGE_URL WHERE MOVIE_TITLE = ? or MOVIEID=? and rownum=1");
           System.out.println("MovieTitle==="+movieTitle);
           System.out.println("movieId==="+movieId);
            pstmt.setString(1, movieTitle);
            if("".equalsIgnoreCase(movieId)){
            	 pstmt.setInt(2, Integer.parseInt("0"));
            }else{
            	 pstmt.setInt(2, Integer.parseInt(movieId));
            }
           
            resultSet = pstmt.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                	System.out.println("Inside");
                	    	posterUrl = resultSet.getString("IMAGE_URL");
                	System.out.println("URL---"+posterUrl);
                	
                }
                
            }
        } catch (SQLException ex) {
           System.out.println("An Exception has getImdbPost" +ex);
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
                        + " conn - fetchIndices! " + excep);
            }
          }

      
        return posterUrl;
    }

}
