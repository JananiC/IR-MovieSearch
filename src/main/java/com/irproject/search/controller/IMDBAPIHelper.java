package com.irproject.search.controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import com.irproject.search.bean.IMDBApiBean;

public class IMDBAPIHelper {
	
	
	  
    
    URL url = null;  
    Scanner sc = null;  
    String apiurl="http://www.deanclatworthy.com/imdb/";  
    String dataurl=null;  
    String retdata=null;  
    InputStream is = null;  
    DataInputStream dis = null;  
    public IMDBApiBean callImdbapi(String moviename){
    	  IMDBApiBean imdbapibean = new IMDBApiBean();
    	  try{  
    		     //Check if user has inputted nothing or blank  
    		     if(moviename==null || moviename.equals("")){  
    		      System.out.println("No movie found");  
    		      System.exit(1);  
    		     }  
    		    
    		    moviename=moviename.replace(" ","+");  
    		     
    		   
    		     //Forming a complete url ready to send (type parameter can be JSON also)  
    		     dataurl=apiurl+"?q="+moviename + "&type=text";  
    		          
    		   //  System.out.println("Getting data from service");  
    		   //  System.out.println("########################################");  
    		       
    		     url = new URL(dataurl);     
    		       
    		     is = url.openStream();  
    		     dis  = new DataInputStream(new BufferedInputStream(is));  
    		       
    		     String details[];  
    		     //Reading data from url  
    		    int i=0;
    		     while((retdata = dis.readLine())!=null){  
    		    	 i++;
    		      //Indicates that movie does not exist in IMDB databse  
    		      if(retdata.equals("error|Film not found")){  
    		       System.out.println("No such movie found");  
    		       imdbapibean = null;
    		       break;  
    		      }  
    		      retdata=retdata.replace("|","#");  
    		      details=retdata.split("#");  
    		     
    		         if("IMDBID".equalsIgnoreCase(details[0])) {
    		        	 imdbapibean.setImdbid(details[1]);
    		         } else if("YEAR".equalsIgnoreCase(details[0])){
    		        	 imdbapibean.setImdbYear(details[1]);
    		         }else if("TITLE".equalsIgnoreCase(details[0])){
    		        	 imdbapibean.setImdbTitle(details[1]);
    		         }else if("IMDBURL".equalsIgnoreCase(details[0])){
    		        	 imdbapibean.setImdburl(details[1]);
    		         }
    		     }    
    		          
    		    }    
    		    catch(Exception e){  
    		     System.out.println(e);  
    		    }  
    		    finally{  
    		     try{  
    		       
    		      if(dis!=null){  
    		       dis.close();  
    		      }  
    		        
    		      if(is!=null){  
    		       is.close();  
    		      }  
    		        
    		      if(sc!=null){  
    		       sc.close();  
    		      }  
    		     }  
    		     catch(Exception e2){  
    		      ;  
    		     }  
    		    }
		return imdbapibean;  
    }
      
    
  
       
         
   

}
