/**
 * 
 */
package com.irproject.search.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.execution.RequestContext;
import org.xeustechnologies.googleapi.spelling.SpellChecker;
import org.xeustechnologies.googleapi.spelling.SpellCorrection;
import org.xeustechnologies.googleapi.spelling.SpellResponse;
import org.xml.sax.SAXException;

import com.irproject.search.Indexing.Indexer;
import com.irproject.search.Indexing.SearchEngine;
import com.irproject.search.bean.IMDBApiBean;
import com.irproject.search.bean.LoginBean;
import com.irproject.search.bean.NERBean;
import com.irproject.search.bean.RecommenderBean;
import com.irproject.search.bean.SearchBean;
import com.irproject.search.bean.SearchTypes;
import com.irproject.search.dao.RecommendationInter;
import com.irproject.search.dao.SearchEngineInter;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

/**
 * @author JANANI
 * @param <Annotation>
 *
 */
public class SearchEngineController extends MultiAction{
	
	private SearchTypes searchTypes;
	
	private RecommendationInter recommenderInter;

	public RecommendationInter getRecommenderInter() {
		return recommenderInter;
	}

	public void setRecommenderInter(RecommendationInter recommenderInter) {
		this.recommenderInter = recommenderInter;
	}
	private SearchEngineInter searchEngineInter;

	
	public SearchEngineInter getSearchEngineInter() {
		return searchEngineInter;
	}

	public void setSearchEngineInter(SearchEngineInter searchEngineInter) {
		this.searchEngineInter = searchEngineInter;
	}

	
	public SearchTypes getSearchTypes() {
		return searchTypes;
	}

	public void setSearchTypes(SearchTypes searchTypes) {
		this.searchTypes = searchTypes;
	}

	public Map loadSearchType(RequestContext requestContext
			) throws ServletException {
		System.out.println("Inside loadSearchType");
		Map model = new HashMap();
		System.out.println("print getSearchTypes()--"+getSearchTypes());
		SearchTypes srch = getSearchTypes();
		Map map =srch.getSearchType();
		Set key = map.entrySet();
		Iterator it = key.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
		model.put("ddValues",getSearchTypes());
			model.put("success", true);
		return model;
	}

	public Map verifyLogin(RequestContext requestContext, LoginBean loginBean){
		System.out.println("Inside verifyLogin");
		
		Map map = new HashMap();
		String user = loginBean.getUserId();
		String password = loginBean.getPassword();
		if(user!=null && user.equalsIgnoreCase("janani") || user.equalsIgnoreCase("user1")) {
			if(password!=null && password.equalsIgnoreCase("janani") || password.equalsIgnoreCase("user1")){
				map.put("success", true);
			}
		} else{
			boolean status = getSearchEngineInter().fetchMovieList(user);
			if(status){
			map.put("success", true);
			} else{
				map.put("failure", true);	
			}
		} 
		requestContext.getFlowScope().put("user", user);
		SearchBean searchBean = (SearchBean)requestContext.getFlowScope().get("searchBean");
		searchBean.setSearchId("");
		searchBean.setSpellcheck("");
		requestContext.getFlowScope().put("searchBean", searchBean);
		requestContext.getFlowScope().put("movieList", "");
		requestContext.getFlowScope().put("recommendation", "");
		return map;
	}
	
	public Map logout(RequestContext requestContext){
		System.out.println("Inside verifyLogin");
		Map map = new HashMap();
		
		requestContext.getFlowScope().put("user", "");
		requestContext.getFlowScope().put("pass", "");
		SearchBean searchBean = (SearchBean)requestContext.getFlowScope().get("searchBean");
		searchBean.setSearchId("");
		requestContext.getFlowScope().put("searchBean", searchBean);
		requestContext.getFlowScope().put("movieList", "");
		requestContext.getFlowScope().put("recommendation", "");
		return map;
	}
	
	
	public Map searchIndex(RequestContext requestContext, SearchBean searchBean) throws IOException, ParserConfigurationException, SAXException{
		requestContext.getFlowScope().put("movieList", "");
		requestContext.getFlowScope().put("recommendation", "");
		
		System.out.println("Inside searchIndex");
		Map map = new HashMap();
		String searchQuery = searchBean.getSearchId();
		String searchType = searchBean.getSearchType();
		System.out.println("Search Query-->"+searchQuery);
		System.out.println("Search Type-->"+searchType);
		
		
		/*List spellCheckList = spellCheck(searchBean);
		if(spellCheckList.size()!=0){
			map.put("spellcheck",spellCheckList);
			requestContext.getFlowScope().put("spellcheck", spellCheckList);
		}else{*/
		
		String splitStrings = spellCheck(searchQuery);
		if(null!=splitStrings){
		String[] arrayst = splitStrings.split("#");
		System.out.println("length of misspelled words--"+arrayst.length);
		if(arrayst.length!=0 ){
			searchBean.setSpellcheck(splitStrings);
			searchQuery = searchQuery+splitStrings;
		}
		}
		
		//===================================
		
		
		
		List<RecommenderBean> recommenderList =null;		
		List<IMDBApiBean> movieList = null ;
		

	      try {
	        
	 
	      	// configure index properties
	        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_41);
	        File dataDir = new File(System.getProperty("jboss.server.data.dir"));
	        //System.out.println("current JBOSS DIRECTORY--"+dataDir.getAbsolutePath());
      	  	//the location to store the indexes in the JBOSS WORKING DIRECTORY
	        File yourFile = new File(dataDir, "/index-demo/index-directory");
	        
	        Directory indexDir = new SimpleFSDirectory(yourFile);
	      // String [] ftoindex={"content","language","title","actor","actress","company","director","genre","plots","locations"};
	      	// build a lucene index
	        System.out.println("---rebuildIndexes");
	        Indexer indexer = new Indexer(indexDir, analyzer);
	       //indexer.rebuildIndexes(); //======COMMENT THIS LINE ONCE THE OFFLINE INDEXING IS DONE######
	        System.out.println("---rebuildIndexes done");

	        // perform searches and retrieve the results
	     String[] ftoindex = null;
	     
	     	        
	        if(searchType .equalsIgnoreCase("ALL")){
	        	//call NLP
	        	
	        	ftoindex = nlpCheck(searchQuery);
	        } else {
	        	 ftoindex = identifyIndexToSearch(searchType);
	        }
	        
	        
	        
	        SearchEngine searcher =  new SearchEngine(indexDir, analyzer, ftoindex);;
	        movieList = search (searcher, searchQuery);
	       
	        String user = (String)requestContext.getFlowScope().get("user");
	        recommenderList = callRecommendation(user,movieList);
	      
	      } catch (Exception e) {
	        System.out.println("Exception caught.\n");
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	      }
	  
		requestContext.getFlowScope().put("movieList", movieList);
		requestContext.getFlowScope().put("recommendation", recommenderList);
		requestContext.getFlowScope().put("searchBean", searchBean);
		
		
		map.put("success", true);
		
	
		return map;
	}
	String firstTitle = null;
	
	public List<IMDBApiBean> search (SearchEngine searcher, String query) 
		    throws IOException, ParseException {	
		
				System.out.println("---Query: " + query);
		        List<IMDBApiBean> movieList = null;
		        IMDBAPIHelper imdbHelper = new IMDBAPIHelper();   
		        
		        ScoreDoc[] hits = searcher.performSearch(query);
		        System.out.println("---Results found: " + hits.length);
		        if(hits.length!=0){
		        	movieList = new ArrayList<IMDBApiBean>();
		        }
		        for(int i=0;i<hits.length;++i) {
		       IMDBApiBean individualMovieMap = new IMDBApiBean();
		          int docId = hits[i].doc;
		          Document doc = searcher.getDoc(docId);
		          String mtitle =doc.get("title");
		          mtitle= mtitle.replace("[", "");
		          mtitle= mtitle.replace("]", "");
		          individualMovieMap.setMovieTitle(mtitle);
		          individualMovieMap.setImdbRating(doc.get("rating"));
		          individualMovieMap.setPlots(doc.get("plots"));
		          individualMovieMap.setLocations("locations");
		       if(i==0){
		    	   firstTitle = mtitle;
		       }
		          System.out.println("\n Matching titles");
		          System.out.println("Plots title --"+individualMovieMap.getMovieTitle());
		          
		         String moviename = individualMovieMap.getMovieTitle().trim();
	    		     String posterUrl = getSearchEngineInter().getImdbPost(moviename,""); 
	    		     individualMovieMap.setPosterUrl(posterUrl);
		          
		         IMDBApiBean imdbApiBean = imdbHelper.callImdbapi(moviename);
		          if(imdbApiBean!=null){
		        	  String imdbTitle = imdbApiBean.getImdbTitle();
		        	  String movietitle = individualMovieMap.getMovieTitle();
		        	  System.out.println("Imdb title----"+imdbTitle);
		        	
		        	  if(imdbTitle!=null && imdbTitle.equalsIgnoreCase(movietitle)){		        	
		        	  individualMovieMap.setImdbid(imdbApiBean.getImdbid());
		        	  //individualMovieMap.setImdbTitle( imdbApiBean.getImdbTitle());
		        	  individualMovieMap.setImdbYear(imdbApiBean.getImdbYear());
		        	  individualMovieMap.setImdburl( imdbApiBean.getImdburl());
		        	 
		        	  }
		          }
		          movieList.add(individualMovieMap);
		        }
		        System.out.println("---end of query results");
		        return movieList;
	}
	
	
	private enum SearchType {
		 ACTRESS, COMP, ACTOR, TL,DIRECTOR,GENRE,LANGUAGE,PLOTS,LOC;
	}
	public String[] identifyIndexToSearch(String searchTyp){
		 
		
		SearchType searchType = SearchType.valueOf(searchTyp);		
	    ArrayList<String> ftoindexArray =  new ArrayList<String>();
	    
	  	    
		 switch (searchType) {
        
         case ACTRESS:  ftoindexArray.add("actress");
                  break;
         case COMP: ftoindexArray.add("company");
                  break;
         case ACTOR:  ftoindexArray.add("actor");
                  break;
         case TL: ftoindexArray.add("title");
                  break;
         case DIRECTOR:  ftoindexArray.add("director");
                  break;        
         case GENRE:  ftoindexArray.add("genre");
         		  break; 
         case LANGUAGE:  ftoindexArray.add("language");
		  break;
         case PLOTS:  ftoindexArray.add("plots");
		  break;
		  
         case LOC:  ftoindexArray.add("locations");
		  break;
         default:ftoindexArray.add("content");
                  break;
     }
		 System.out.println("result of ftindexArray in nlpcheck--"+ftoindexArray);
		 String[] ftoIndex = null;
		 if(null!=ftoindexArray){
		  ftoIndex =  (String[]) ftoindexArray.toArray(new String[0]);
		 }
		 return ftoIndex;
			
	}
	
	public String[] nlpCheck(String queryString) throws IOException,
			ParserConfigurationException, SAXException {
		System.out.println("Inside verifyLogin");
		// Map map = new HashMap();
		ArrayList<String> ftoindexArray = new ArrayList<String>();

		Properties props = new Properties();
		props.put("annotators", "tokenize,ssplit,pos,lemma,parse,ner");

		File dataDir = new File(System.getProperty("jboss.server.data.dir"));
		System.out.println(dataDir.getAbsolutePath());
		File yourFile = new File(dataDir.getAbsolutePath() + "/output.xml");
		String outputXML = yourFile.getAbsolutePath();
		PrintWriter xmlOut = new PrintWriter(outputXML);

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		Annotation annotation = new Annotation(queryString);

		pipeline.annotate(annotation);

		if (xmlOut != null) {
			pipeline.xmlPrint(annotation, xmlOut);
		}
		ParseXMLHelper parse = new ParseXMLHelper();
		List<NERBean> nerList = parse.getTags(yourFile.getAbsolutePath());
		// Location, Person, Organization , Misc
		for (NERBean nerbean : nerList) {
			String nerString = nerbean.getNer();
			if (null != nerString && nerString.equalsIgnoreCase("MISC")) {
				ftoindexArray.add("language");
				ftoindexArray.add("genre");
			} else if(null != nerString && nerString.equalsIgnoreCase("LOCATION")){
				ftoindexArray.add("locations");
			}
			else if (null != nerString && nerString.equalsIgnoreCase("Organization")) {
				ftoindexArray.add("company");
			} else if (null != nerString
					&& nerString.equalsIgnoreCase("Person")) {
				ftoindexArray.add("actor");
				ftoindexArray.add("actress");
				ftoindexArray.add("director");
			} else {
				ftoindexArray.add("content");
			}

		}
		System.out.println("result of ftindexArray in nlpcheck--"+ftoindexArray);
		String[] ftoIndex = null;
		if(null != ftoindexArray) {
		ftoIndex =  (String[]) ftoindexArray.toArray(new String[0]);
		}
		return ftoIndex;
	}
	

	
	
	
	public  List<RecommenderBean> callRecommendation(String user, List<IMDBApiBean> movieList){
		  List<RecommenderBean> recomBeanList =null;
		  List<RecommenderBean> finalListRec = new ArrayList<RecommenderBean>();
		  List<RecommenderBean> recommenderList = new ArrayList<RecommenderBean>();
		  IMDBAPIHelper imdbHelper = new IMDBAPIHelper();
		 if(null!=movieList){
	        	
	        	System.out.println("USER Retrieved from SESSSSION!!!!!!!!!!!"+user);
	        	
	     
	        	 if("janani".equalsIgnoreCase(user)){

		        	   if(firstTitle!=null){
		   	        	String firstMovieId  =getRecommenderInter().getMovieId(firstTitle.trim());
		   	        	if(null!=firstMovieId) {
		   	        	recomBeanList = getRecommenderInter().getMoviesFromMoviesCluster(firstMovieId);
		   	        	if(null!=recomBeanList){
		   	        	  Iterator it = recomBeanList.iterator();
		   	  	        while(it.hasNext()){
		   	  	        	RecommenderBean recBean = (RecommenderBean)it.next();
		   	  	        	String movieId = recBean.getRecMovieId();
		   	  	        	//String recTitle = getRecommenderInter().getMovieTitle(movieId);
		   	  	        	String imageurl = getSearchEngineInter().getImdbPost("",movieId);
		   	  	        	recBean.setImageUrl(imageurl);
		   	  	        	finalListRec.add(recBean);
		   	  	        }
		   	        	}
		   	        	}
		   	        }
		        
		        }
	        	else if(!"".equalsIgnoreCase(user) && null!=user){

		   	        	recomBeanList = getRecommenderInter().getMoviesFromUserCluster(user);
		   	        	if(null!=recomBeanList){
		   	        	  Iterator it = recomBeanList.iterator();
		   	  	        while(it.hasNext()){
		   	  	        	RecommenderBean recBean = (RecommenderBean)it.next();
		   	  	        	String movieId = recBean.getRecMovieId();
		   	  	        	//String recTitle = getRecommenderInter().getMovieTitle(movieId);
		   	  	        	String imageurl = getSearchEngineInter().getImdbPost("",movieId);
		   	  	        	recBean.setImageUrl(imageurl);
		   	  	        	finalListRec.add(recBean);
		   	  	        }
		   	        	}
		   	                	
	        	} else{
	        	   if(firstTitle!=null){
	   	        	String firstMovieId  =getRecommenderInter().getMovieId(firstTitle.trim());
	   	        	if(null!=firstMovieId) {
	   	        	recomBeanList = getRecommenderInter().getMoviesFromMoviesCluster(firstMovieId);
	   	        	if(null!=recomBeanList){
	   	        	  Iterator it = recomBeanList.iterator();
	   	  	        while(it.hasNext()){
	   	  	        	RecommenderBean recBean = (RecommenderBean)it.next();
	   	  	        	String movieId = recBean.getRecMovieId();
	   	  	        	//String recTitle = getRecommenderInter().getMovieTitle(movieId);
	   	  	        	String imageurl = getSearchEngineInter().getImdbPost("",movieId);
	   	  	        	recBean.setImageUrl(imageurl);
	   	  	        	finalListRec.add(recBean);
	   	  	        }
	   	        	}
	   	        	}
	   	        }
	        	  
	        }
	      
	     
	      //compare movies
	        Iterator comp = finalListRec.iterator();
	        System.out.println("\n\n");
	        while(comp.hasNext()){
	        	boolean compFlag = false;
	        	RecommenderBean finalRecbean = (RecommenderBean)comp.next();
	        	Iterator movieIter = movieList.iterator();
	        	while(movieIter.hasNext()){
	        	IMDBApiBean movieBean = (IMDBApiBean)movieIter.next();
	        	String movId  =getRecommenderInter().getMovieId(movieBean.getMovieTitle().trim());
	        	System.out.println("comparing Search Res Mov Id---"+movId+"  recommender mov Id"+finalRecbean.getRecMovieId());
	        	if(null != movId && movId.equalsIgnoreCase(finalRecbean.getRecMovieId())){
	        		compFlag = true;
	        	}
	        	}
	        	if(!compFlag){
	        		
	        		String recTitle = getRecommenderInter().getMovieTitle(finalRecbean.getRecMovieId());
	        		 IMDBApiBean imdbApiBean = imdbHelper.callImdbapi(recTitle);
	        		 if(null!=imdbApiBean){
	        		 finalRecbean.setImdbUrl( imdbApiBean.getImdburl());
	        		 }
	        	recommenderList.add(finalRecbean);
	        	}
	        }
	        }
		 return recommenderList;
	}
	
	  public  String spellCheck (String sentence) {
		  SpellChecker checker = new SpellChecker();
		  String res  = "";
			SpellResponse spellResponse = checker.check(sentence);
			if (spellResponse != null) {
				SpellCorrection[] scArr = spellResponse.getCorrections();
				if(scArr == null) {
					return null;
				} else {
					
					
				    String se = "";
	                System.out.println("Did you mean");
	                int xmin=100;
	                int xpos=0;
	                int[] dist = null;
	                String [] words = null;
	                for( SpellCorrection sc : scArr )
	                {
	                    words=sc.getWords();
	                    dist=new int[words.length];
	                    xmin=100;
	                    xpos=0; 
	                    for(int i=0;i<words.length;i++)
	                    {
	                    	
	                        dist[i]=distance(sentence, words[i]);
	                        if(dist[i]<=xmin&& !words[i].contains("-")&&!words[i].contains(" ") )
	                        {
	                            xmin=dist[i];
	                            xpos=i;
	                        }
	                    }
	                    
	                    if(res.equals("")){
							 res=words[xpos]; 
						}else
			            	 res= res+" "+words[xpos]; 
	                }
	              
					
	                   
					 System.out.println(res);
				/*	System.out.println("Suggestions include:");
					for( SpellCorrection sc : scArr )
						//	se += sc.getValue() + " ";
							System.out.println( sc.getValue() );
					return "Please input correct key words";*/
				}
				return res;
			}
			return null;
	  }
	  public static int distance(String s1, String s2){
	         int edits[][]=new int[s1.length()+1][s2.length()+1];
	         for(int i=0;i<=s1.length();i++)
	             edits[i][0]=i;
	         for(int j=1;j<=s2.length();j++)
	             edits[0][j]=j;
	         for(int i=1;i<=s1.length();i++){
	             for(int j=1;j<=s2.length();j++){
	                 int u=(s1.charAt(i-1)==s2.charAt(j-1)?0:1);
	                 edits[i][j]=Math.min(
	                                 edits[i-1][j]+1,
	                                 Math.min(
	                                    edits[i][j-1]+1,
	                                    edits[i-1][j-1]+u
	                                 )
	                             );
	             }
	         }
	         return edits[s1.length()][s2.length()];
	    }
}
