<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%--Kristen created this page --%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Browse Auctions</title>
<style>
h1 {text-align:center;}
h3 {font-weight:bold; font-style:italic; padding:0px; margin:0px;}
table, th, td {padding:15px;}
</style>
</head>

<body>

<h1>Browse Auctions</h1>
<b><a href="${pageContext.request.contextPath}/jsps/advsearch.jsp">Advanced Search</a></b><br>
<br>

<b>Sort Auctions</b>
<form method="get" action="${pageContext.request.contextPath}/sortby">
	<select name="sort">
		<option id="no" value="no" name="no">None</option>
		<option id="plh" value="plh" name="plh">Price: Low to High</option>
		<option id="phl" value="phl" name="phl">Price: High to Low</option>
		<option id="btype" value="btype" name="btype">Book Type</option>
	</select>
	<input type="submit" name="Sort" value="Sort">
</form>
<br>

<table>
<c:forEach items="${sr}" var="r">
	<tr>
		<td>
			<a href="${pageContext.request.contextPath}/auctionview?auctionid=${r.auctionID}">Auction ID #${r.auctionID}</a>
			<h3>${r.title}</h3>
			<b>By ${r.author}</b><br>
			Sold by ${r.seller}<br>
			Current bid: <fmt:formatNumber type="CURRENCY">${r.price}</fmt:formatNumber><br>
		</td>
	</tr>
</c:forEach>
</table>

</body>
</html>