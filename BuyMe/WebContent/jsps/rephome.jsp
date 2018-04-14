	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style>
h1 {text-align:center;}
h2 {text-align:left;}

ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: #333;
}

li {
    float: right;
}

li a {
    display: block;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Rep Home Page</title>
</head>


<body>

<div id="navBar">
<ul>
	<li><a href="${pageContext.request.contextPath}/logout">Logout</a> </li>
	<li><a href="${pageContext.request.contextPath}/createauction">Create Auction</a></li>
	<li><a href="${pageContext.request.contextPath}/browse">Browse</a></li>
	<li><a href="${pageContext.request.contextPath}/Forum">Forum</a></li>
	<li><a href="${pageContext.request.contextPath}/home">Home</a></li>
</ul>
</div>
<h1> Welcome Representative!</h1>



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
<h3> Delete a bid</h3>
<br>
<form action="${pageContext.request.contextPath}/manageauction" method="post">
	<input type="text" name="username" placeholder="Username" size=50> 
	<br>
	<input type = "text" name = "auctionid" placeholder = "Auction ID" size = 50>
	<br>
	<button type="submit" name="button" value="delBid">Submit</button>

</form>

<c:if test="${not empty delbsuccess }">

	<c:if test = "${delbsuccess }">
		<text>Successfully deleted bid</text>
	</c:if>
	
	<c:if test = "${not delbsuccess }">
		<text>Unable to delete bid</text>
	</c:if>


</c:if>
<br>
<br>


<h3> Remove an Auction</h3>
<br>
<form action="${pageContext.request.contextPath}/manageauction" method="post">
	<input type = "text" name = "auctionid" placeholder = "Auction ID" size = 50>
	<br>
	<button type="submit" name="button" value="delAuc">Submit</button>

</form>

<br>
<c:if test="${not empty delasuccess }">

	<c:if test = "${delasuccess }">
		<text>Successfully deleted Auction</text>
	</c:if>
	
	<c:if test = "${not delasuccess }">
		<text>Unable to delete Auction</text>
	</c:if>


</c:if>
<br>



</body>
</html>