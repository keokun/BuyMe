<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style>
h1 {text-align:center;}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Rep Home Page</title>
</head>


<body>
<h1> Welcome Representative!</h1>
<%out.println(request.getAttribute("firstname"));%> <%out.println(request.getAttribute("lastname"));%>'s Home



<br>
<br>

<h3> Change user's account password</h3>
<br>
<form action="${pageContext.request.contextPath}/manageaccount" method="post">
	<input type="text" name="username" placeholder="Username" size=50> 
	<br>
	<input type = "text" name = "password" placeholder = "Password" size = 50>
	<br>
	<button type="submit" name="button" value="resPass">Submit</button>

</form>

<br>
<br>

<c:if test="${not empty resetsuccess}">

	<c:if test = "${resetsuccess}">
		<text><%out.print(request.getAttribute("username")); %>'s password has been changed </text>
	</c:if>
	<br>
	<c:if test = "${not resetsuccess}">
		<text>Unable to change password for that user.</text>
	</c:if>
	
</c:if>

<br>
<br>

<a href="${pageContext.request.contextPath}/AuctionHistory?user=${username}"> Auction History</a>
<br>
<br>
<a href="${pageContext.request.contextPath}/Forum">Forum</a>

<br>
<br>

<a href="${pageContext.request.contextPath}/home">Logout</a>

</body>
</html>