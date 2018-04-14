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

li a:hover {
    background-color: #111;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Page</title>

</head>

<div id="navBar">
<ul>
	<li><a href="${pageContext.request.contextPath}/logout">Logout</a> </li>
	<li><a href="${pageContext.request.contextPath}/createauction">Create Auction</a></li>
	<li><a href="${pageContext.request.contextPath}/browse">Browse</a></li>
	<li><a href="${pageContext.request.contextPath}/Forum">Forum</a></li>
	<li><a href="${pageContext.request.contextPath}/home">Home</a></li>
</ul>
</div>

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

<c:if test="${not empty success}" >
	<c:if test="${success}" >
		<text>Account Creation Was Successful </text>
	</c:if>
	
	<c:if test="${not success}" >
		<text>Account Creation Failed </text>
	</c:if>
	
</c:if>

<br>
<br>
* Indicates a required field.
<br>
<br>

<h2> Delete a regular account</h2>
<br>
<form action="${pageContext.request.contextPath}/manageaccount" method="post">
	<input type="text" name="username" placeholder="Username" size=50> 
	<br>
	<button type="submit" name="button" value="delAcc">Submit</button>

</form>
<br>
<br>

<c:if test="${not empty delsuccess}">

	<c:if test = "${delsuccess}">
		<text><%out.print(request.getAttribute("username")); %> was successfully deleted </text>
	</c:if>

	<c:if test = "${not delsuccess}">
		<text>Unable to delete account.</text>
	</c:if>
	
</c:if>



<%-- The sales report section --%>
<br>
<br>
<br>
<h3>Sales Reports</h3>
<form action="${pageContext.request.contextPath}/SalesReport" method="post">

    <input type= "radio" name = "rtype" value = "TotEarn">Total Earnings
    <br>
    <input type= "radio" name = "rtype" value = "EpItem">Earnings per item
    <br>
    <input type= "radio" name = "rtype" value = "EpItemType">Earnings per item type
    <br>
    <input type= "radio" name = "rtype" value = "EpEndUser">Earnings per end-user
    <br>
    <input type= "radio" name = "rtype" value = "BSitems">Best-Selling Items
    <br>
    <input type= "radio" name = "rtype" value = "BBuyers">Best Buyers 
    <br>



    <input type="submit" value=Submit>

</form>

<c:if test = "${not empty sres}">
	<c:if test = "${sres}">
		<text> SalesReport Retrieval Success! </text>
		
		<br>
		
		<%--If we are just taking the earnings --%>
		<c:if test = "${justSum}">
		
			<%--Just display the earnings --%>
			<text>Total Earnings</text>
			
			
			<c:forEach items = "${salestable}" var = "sitem">
				<tr>
					<td>${sitem.res}</td>
				</tr>
			</c:forEach>
			
			
		</c:if>
		
		<%--If we are taking earnings of specific categories --%>
		<c:if test = "${not justSum}">
		
			<table>
				<TH> Earnings </TH>
				<TH> <%request.getAttribute("cat"); %></TH>
				
				
				<c:forEach items = "${salestable}" var = "sitem">
					<tr>
						<td>${sitem.res}</td>
						<td>${sitem.stat}</td>
					</tr>
				</c:forEach>
				
				
			</table>	
			
		</c:if>

	
	
	</c:if>
	
	<c:if test = "${not sres }">
		<text> SalesReport Retrieval Failure. </text>
	</c:if>

</c:if>


</body>
</html>