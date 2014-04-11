/**
 * 
 */
package com.irproject.search.controller;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.irproject.search.dao.SearchEngineInter;

/**
 * @author JANANI
 *
 */
public class AjaxBuilder extends MultiActionController {
private SearchEngineInter searchEngineInter;

	
	public SearchEngineInter getSearchEngineInter() {
		return searchEngineInter;
	}

	public void setSearchEngineInter(SearchEngineInter searchEngineInter) {
		this.searchEngineInter = searchEngineInter;
	}
	
	  public final ModelAndView loadAutoCompleteData(HttpServletRequest request, HttpServletResponse response) {
	       System.out.println("loadAutoCompleteData Start");
	        try {
	        StringBuffer jsonObj = new StringBuffer();
	         /*    String indexValue ="firstIndex";
	             String index2 ="janani";
	             String index3 ="Gomathy";
	             String index4="priya";*/
	             response.setContentType("application/json");
	             
	            ArrayList<String> indexList =  getSearchEngineInter().fetchIndices();
	             
	           /*  { "Index" :["Janani","Goamthy","Priya"]

	             }*/
	            jsonObj.append("{\"Index\":[\"");
	            Iterator indexIter = indexList.iterator();   
	            while(indexIter.hasNext()) {
	            	 jsonObj.append(indexIter.next()+"\"");
	     	        jsonObj.append(",\"");
	            }
	              
	            jsonObj.append("\"");
	       
	        /*jsonObj.append(index2+"\"");
	        jsonObj.append(",\"");
	        jsonObj.append(index3+"\"");
	        jsonObj.append(",\"");
	        jsonObj.append(index4+"\"");*/
	        jsonObj.append("]}");
	        
	       // System.out.println("json string " + jsonObj);
	        
	        
	        response.getWriter().println(jsonObj);
	        
	        
	        } catch (Exception e) {
	        	 System.out.println("Exception occurred"+ e);
	        }
	        System.out.println("loadAutoCompleteData End");
	        return null;
	    }



}
