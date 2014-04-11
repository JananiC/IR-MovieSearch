
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
	background-image:url('<%=request.getContextPath()%>/images/filmtape.jpg');
	background-repeat:no-repeat;

	background-position:right; 
	   width:1000px;
    height:600px;
	
}
.left-welcome{

 position: absolute;
    left: 50%;
    margin-left: -250px;
  
    width:500px;
    height:357px;

}
.myButton {
    background:url('<%=request.getContextPath()%>/images/login-button.png') no-repeat;
     cursor:pointer;
    width: 200px;
    height: 100px;
    border: none;
}

</style>
</head>

<body >
<center><h1>Sign In</h1></center>


<form name="loginPage" id="loginBean"  modelAttribute="loginBean" style="height:100%;" method="post" action="${flowExecutionUrl}" >

<center>
<table>
  <tr><td> <h2>User Id</h2></td>    
  <td><input type="text" id="userId" name="userId"></input></td> 
  </tr>
  <tr>
  <td><h2>Password</h2></td>
  <td><input type="password" id="password" name="password"></input></td>
  </tr>
  <tr><center> <td>        
  <input value="" id="login" class="myButton" name="_eventId_login" type="submit"/>
  </td></center></tr>
</table>

</center>
 
<input type="hidden" name="_flowExecutionKey" value="$(flowExecutionKey}"/>
</form>
</body>
</html>

