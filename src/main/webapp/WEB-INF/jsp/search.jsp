<%@page import="com.irproject.search.bean.SearchTypes"%>
<%@page import="com.irproject.search.bean.SearchBean"%>
<%@page import="com.irproject.search.bean.IMDBApiBean"%>
<%@page import="com.irproject.search.bean.RecommenderBean"%>


<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/spring.tld" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@taglib prefix="core" uri="http://www.springframework.org/tags"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
<title>Search Engine IR Project</title>


<style type="text/css">
body {
	background:black;
	font: 11px/16px Tahoma, Arial, Verdana, sans-serif;
	color: #000;
	margin:15px;
}

.right
{
    float:right;
    margin-right:20px;
    
}
.g-search-button {
 
	border:3px solid #050500;
	border-radius:5px;
	height: 25px;
	width: 500px;
}
.g-search-button1 {
 
	
	height: 35px;
	width: 70px;
}

a {
    color: #0254EB
}
a:visited {
    color: #0254EB
}
a.morelink {
    text-decoration:none;
    outline: none;
}
.morecontent span {
    display: none;
}
.comment {
	font-face:Verdana;
	font-size:13px;
    width: 350px;
     margin: 10px;
}

.movieName {
	color: #333;
	font-size: 15px;
	font-weight: bold;
	top: 30px;
}

.titlePageSprite {
	background-image: url(images/titlePageSprite-2288315300._V_.png);
	display: inline-block;
	vertical-align: middle;
}

.star-box-giga-star {
	background-position: -180px -71px;
	width: 66px;
	height: 66px;
	line-height: 66px;
	display: inline-block;
	text-align: center;
	vertical-align: middle;
	font-size: 15px;
	font-weight: bold;
	color: black;
	font-family: tahoma;
	float: left;
	padding-right: 5px;
}
.table1{


padding:10px;
border:3px solid white;
border-radius:10px;
margin:0px;}


.centerMenu {  
    background:black;
    width: 100%;  
 }          
.leftMenu {
background:#E5F6FF;  
   float: left;  
   width: 20%;  
}  
.rightMenu {  
background:#FCFCFC; 
   float: left;  
   width: 80%;  
}  
.rec_heading {
color: #a58500;
font-size: 17.5px;
margin: 0 0 .5em;
padding: 0;
}
.recommend{
color: rgb(165, 133, 0);
display: inline;
font-family: Verdana, Arial, sans-serif;
font-size: 18px;
font-weight: bold;
height: auto;
width: auto;
}
</style>
</head>

<body >
<% 


Map map = (Map) request.getAttribute("res");
SearchBean searchb = null;
SearchTypes dd = (SearchTypes) map.get("ddValues"); 
 Map ddValues;
 if(null!=request.getAttribute("movieList") && !request.getAttribute("movieList").equals("")){
 List<IMDBApiBean> movieList = (List<IMDBApiBean>) request
 .getAttribute("movieList");
 }
 if(null!=request.getAttribute("recommendation") && !request.getAttribute("recommendation").equals("")){
 List<RecommenderBean> recommendation = (List<RecommenderBean>) request.getAttribute("recommendation");
 }
 if(null!=request.getAttribute("searchBean") && !request.getAttribute("searchBean").equals("")){
	  searchb = (SearchBean) request.getAttribute("searchBean");
 }
 String user = (String)request.getAttribute("user");
if(map.get("spellcheck")!=null){ 
 List<String> spellList = (List<String>) map.get("spellcheck");
}
 %>
 
<form name="searchPage" id="searchBean"  modelAttribute="searchBean" style="height:100%;" method="post" action="${flowExecutionUrl}" >

<div class="centerMenu"> <!-- Main Div -->

<center><font style=font-family:Impact color="white" size="6" >Search Movies</font></center>



<c:choose>
    <c:when test="${empty user}">
      <div class="right"><a href="${flowExecutionUrl}&_eventId=loginPage"><font style=font-family:Verdana,Arial,sans-serif COLOR="white" SIZE="4" >Login</font></a></div>
    </c:when>
    <c:otherwise>
        <div class="right">
	<font face="Verdana" COLOR="white" SIZE="3" >Logged in as <%out.println(user);%></font>
	<a href="${flowExecutionUrl}&_eventId=logout"><font style=font-family:Broadway,Arial,sans-serif COLOR="white" SIZE="4" ></font><img src="<%=request.getContextPath()%>/images/download.jpg"/></a>
	</div>
    </c:otherwise>
</c:choose>



<table>
  <tr> 
  <td><img style="width: 150px; height: 100px" src="<%=request.getContextPath()%>/images/mainimage.jpg"/></td>
   <td>
   <spring:bind path="searchBean.searchId">
   <input class="g-search-button" id="searchId" type="text" name="searchId"  value="${searchBean.searchId}"/>
	</spring:bind>
  </td>
  <td>
   <spring:bind path="searchBean.searchType">
            <select name="searchType" class="mini" id="searchType" style="height:30px">
          <% ddValues = (Map) dd.getSearchType();
          	 	Set entrySet = ddValues.entrySet();
	    	 	Iterator it = entrySet.iterator();
	    	 	 while (it.hasNext()) {
	    	 		 
	    			 Map.Entry entry = (Map.Entry) it.next(); 
	    			 if (((String)entry.getKey()).equals(searchb.getSearchType()))
					 {
					  %>
					 <option value ='<%= entry.getKey()%>' selected><%=entry.getValue()%></option>
					
				
				<%  } else { %>
				<option value ='<%= entry.getKey()%>'><%=(String)entry.getValue()%></option>
				<% } %>
				<% }
		     ddValues = null;
		     entrySet = null;
		     it = null; %>
		        </select>
		        
		        
	</spring:bind>
  </td>
  <td>  
<input value="Search"  id="search"  name="_eventId_search" type="submit" style="height:30px;font-size:16pt;color:#4d4207;"/>  
</td>

</tr>

 </table>
  

<c:choose>
    <c:when test="${empty searchBean.spellcheck}">
     
    </c:when>
    <c:otherwise>
       <center><spring:bind path="searchBean.spellcheck">
   <font face="Verdana" COLOR="white" SIZE="4" >Did you mean:</font>
   <a href="http://www.google.com/"></a> <font face="Verdana" color="yellow" size="4"> ${searchBean.spellcheck}</font>

	</spring:bind></center>
    </c:otherwise>
</c:choose>


</div>

<br />



<div id="searchResult" class="rightMenu">
		<table >  
	
 			<c:forEach var="movieResBean1" items="${movieList}" varStatus="rowCounter">
 			<c:if test="${rowCounter.index mod 2 eq 0}">
 				<tr>
 				</c:if>
					 <td >
 						<table class="table1">
							<tbody>
								<tr>
									<td>
									
									
									<c:choose>
   										 <c:when test="${empty movieResBean1.posterUrl}">
    										 <a href="${movieResBean1.imdburl}"> <img style="width: 100px; height: 186px" src="<%=request.getContextPath()%>/images/the_movies.jpg"/>
											</a>
    								</c:when>
   									 <c:otherwise>
       										<a href="${movieResBean1.imdburl}"> <img style="width: 100px; height: 186px" src="${movieResBean1.posterUrl}" />
											</a>
    								</c:otherwise>
									</c:choose>
									
									
									
									
									</td>
									<td valign="top">
										<table>
											<tr>
												<td align="center" class="movieName"><font face="Broadway" COLOR="#800000" SIZE="4" >${movieResBean1.movieTitle}</font></td>
											</tr>
											<tr>
												<td>
													<div class="titlePageSprite star-box-giga-star">${movieResBean1.imdbRating}</div>
												</td>
											</tr>
											<%-- <tr>
												<td>${movieResBean1.year}</td>
												<br>
											</tr> --%>
											<tr>
												<td>
												
												
									<c:choose>
    								<c:when test="${empty movieResBean1.plots}">
     								<div class="comment"> To their astonishment the local sultan Pasta says it's normal to see his whole city in ruins, it's rebuild daily by Nefir and his team of magical dwarfs, because every night it's completely trampled to rubble by the smashing ballet of the giant rhinoceros Samir the Destroyer. Aladdin's remark how crazy that is gets taken as an offer to deal with Samir, but after failed attempts by Genie, Nefir offers to built a catapult, yet soon is found to have his own agenda, involving magical dance shoes, which  Aladdin's side fights in a musical duel.</div>
    								</c:when>
   									 <c:otherwise>
   									 <div class="comment"> ${movieResBean1.plots}</div>
   									  </c:otherwise>
   									  </c:choose>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
 					 </td>
 					<c:if test="${(rowCounter.index+1) mod 2 eq 0 or rowCounter.last}">
        </tr>
    </c:if>
 				</c:forEach>
 			
</table>
</div>



<c:choose>
    <c:when test="${empty recommendation}">
     
    </c:when>
    <c:otherwise>
 <div class="leftMenu"> 

<table width="100%">
<thead>
<h2 class="rec_heading">
<span class="recommend" data-spec="p13nsims:tt1305797" style="display: inline;">Hey, See here!! You may also like this...&nbsp;</span>
</h2>
 </thead>
<c:forEach var="recommend" items="${recommendation}" varStatus="rowCounter">
<tr>
<td align="center"><a href="${recommend.imdbUrl}"> <img style="width: 100px; height: 186px" src="${recommend.imageUrl}" /></td>
<td valign="top">
<table>
<tr>
<td>
<div class="titlePageSprite star-box-giga-star">${recommend.recMovRat}</div>
</td>
</tr>
</table>
</td>
</tr>
</c:forEach>

</table>

</div>
    </c:otherwise>
</c:choose>



<input type="hidden" name="_flowExecutionKey" value="$(flowExecutionKey}"/>
</form>
 <link rel="stylesheet" href="css/jquery-ui.css" />
<script src="script/jquery-1.9.1.min.js"></script>
<script src="script/jquery-ui.js"></script>
<script language="javascript" type="text/javascript" >

$(document).ready(function() {
    
    $(".comment").shorten({
        "showChars" : 200,
        "moreText"  : "See More",
        "lessText"  : "Less",
    });
 
});
/* $("#searchId").keypress(function() {

	  console.log("Handler for .keypress() called.");
	}); */
	
	
	$("#searchId").keypress(function() {
     
      $.getJSON("autoComplete.ir",
                   function(data){
    	 console.log(data.Index);
    	  $( "#searchId" ).autocomplete({
    	      source: data.Index,
    	      select:function(event, ui){   
     	      var value = ui.item.value;    
     	      $("#searchId").val(value);
     	    }
    	    });    	   
		} );     
    });
      
	
	(function($) {
	    $.fn.shorten = function (settings) {
	     
	        var config = {
	            showChars: 100,
	            ellipsesText: "...",
	            moreText: "more",
	            lessText: "less"
	        };
	 
	        if (settings) {
	            $.extend(config, settings);
	        }
	         
	        $(document).off("click", '.morelink');
	         
	        $(document).on({click: function () {
	 
	                var $this = $(this);
	                if ($this.hasClass('less')) {
	                    $this.removeClass('less');
	                    $this.html(config.moreText);
	                } else {
	                    $this.addClass('less');
	                    $this.html(config.lessText);
	                }
	                $this.parent().prev().toggle();
	                $this.prev().toggle();
	                return false;
	            }
	        }, '.morelink');
	 
	        return this.each(function () {
	            var $this = $(this);
	            if($this.hasClass("shortened")) return;
	             
	            $this.addClass("shortened");
	            var content = $this.html();
	            if (content.length > config.showChars) {
	                var c = content.substr(0, config.showChars);
	                var h = content.substr(config.showChars, content.length - config.showChars);
	                var html = c + '<span class="moreellipses">' + config.ellipsesText + ' </span><span class="morecontent"><span>' + h + '</span> <a href="#" class="morelink">' + config.moreText + '</a></span>';
	                $this.html(html);
	                $(".morecontent span").hide();
	            }
	        });
	         
	    };
	 
	 })(jQuery);

</script>
</body>
</html>

