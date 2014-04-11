
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/spring.tld" prefix="spring"%>
<%@taglib prefix="core" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
<title>Search Engine IR Project</title>
<script language="javascript" type="text/javascript" >

</script>

<style type="text/css">
body {
background-image:url('<%=request.getContextPath()%>/images/Picture1.jpg');
background-repeat:no-repeat;
  width:1000px;
   
}
</style>
</head>

<body >
<center><h1>LOGGED OUT</h1></center>


<form name="loginPage" id="loginBean"  modelAttribute="loginBean" style="height:100%;" method="post" action="${flowExecutionUrl}" >

<center>
<font face="Verdana" color="black" size="3" >You have been successfully logged out</font>
<br/>

<font face="Verdana" color="black" size="3" > Please click here to login again</font>

<div><a href="${flowExecutionUrl}&_eventId=login">LOGIN</a>


</div>
<br />
<br />

<div><center><a href="Search.ir"><img src="<%=request.getContextPath()%>/images/enter.jpg"/></a></center>


</div>


</center>
 
<input type="hidden" name="_flowExecutionKey" value="$(flowExecutionKey}"/>
</form>
</body>
</html>

