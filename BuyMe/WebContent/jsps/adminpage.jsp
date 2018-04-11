<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style>
h1 {text-align:center;}
h2 {text-align:left;}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Page</title>

</head>
<h1> Welcome admin</h1>

<body>
<h2>Create a Customer Rep</h2>
<form action="${pageContext.request.contextPath}/createaccount" method="post">
	<input type="text" name="fname" placeholder="First Name" size=50>
	<br>
	<input type="text" name="lname" placeholder="Last Name" size=50>
	<br>
	<input type="text" name="username" placeholder="Username*" size=50> 
	<br>
	<input type="text" name="password" placeholder="Password*" size=50> 
	<br>
	<input type="text" name="email" placeholder="Email*" size=50> 
	<br>
	
	<button type="submit" name="button" value="CR">Submit</button>
</form>



<br>
* Indicates a required field.
<br>
<br>

<c:if test="${not empty success}" >
	<c:if test="${success}" >
		<text>Account Creation Was Successful </text>
	</c:if>
	
	<c:if test="${not success}" >
		<text>Account Creation Failed </text>
	</c:if>
	
</c:if>

<%-- The sales report section --%>
<br>
<br>
<br>
<h3>Sales Reports</h3>
<form action="${pageContext.request.contextPath}/SalesReport" method="post">
  <select name="SReport">
    <option value="TotEarn">Total Earnings</option>
    <option value="EpItem">Earnings per item</option>
    <option value="EpItemType">Earnings per item type</option>
    <option value="EpEndUser">Earnings per end-user</option>
    <option value="BSitems">Best-Selling Items</option>
    <option value="BBuyers">Best Buyers</option>
  </select>
  <input type="submit" name=SubSale value="Get Report">
</form>



</form>


<a href="${pageContext.request.contextPath}/home">Logout</a>


</body>
</html>