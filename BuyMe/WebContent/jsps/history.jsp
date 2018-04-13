<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>History</title>
<style>
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

<br>

<c:if test="${not empty success}">


	<%-- If we are able to get history, printout  --%>
	<c:if test = "${success}">
		<h1>Auction History For ${param.user}</h1>
		<br>
		
		<text><%
		
									int numq = (int)request.getAttribute("newsize");
										if (numq > 1)
										{
											out.print(numq + " auctions\n");
										}
										else if (numq == 0)
										{
											out.print("0 auctions\n");
										}
										else
										{
											out.print("1 auction\n");
										}
											%> </text>
		<br>									
		<br>
		<table>
		
			<c:forEach var = "auction" items = "${ahtable}">
				<tr>
					<text>Open Date: </text><c:out value= "${auction.posttime}" />
					<%out.println("<br>"); %>
				</tr>
				
				<tr>
					Close Date: <c:out value= "${auction.endtime}"/>
					<%out.println("<br>"); %>
				</tr>
				
				<tr>
					Book: <c:out value= "${auction.book}"/>
					<%out.println("<br>"); %>
				</tr>
				<tr>
				
					Seller: <c:out value= "${auction.seller}"/>
					
					<%out.println("<br><br>"); %>
					
				</tr>
		
			</c:forEach>
		
		
		</table>

		
		
		
		
		
		
		
	</c:if>

	<c:if test = "${not success}">
		<text> Auction History Retrieval Failed. </text>
	</c:if>




</c:if>

<c:if test= "${not empty bidsuccess }">
	
	<c:if test= "${bidsuccess }">
	<h1>Bid History for Auction ${param.auctionid}</h1>
		<br>
		<br>
		<text><% int numq = (int)request.getAttribute("newsize");
										if (numq > 1)
										{
											out.print(numq + " bids\n");
										}
										else if (numq == 0)
										{
											out.print("0 bids\n");
										}
										else
										{
											out.print("1 bid\n");
										}
											%> </text>
		
		<br>									
		<br>
		<table>
		
			<c:forEach var = "bid" items = "${bidtable}">
				<tr>
					<text>From: </text><c:out value= "${bid.time}" />
					<%out.println("<br>"); %>
				</tr>
				
				<tr>
					Amount Bid: $<fmt:formatNumber type = "number" maxFractionDigits = "2" minFractionDigits = "2" value = "${bid.amount}"/>
					<%out.println("<br>"); %>
				</tr>
				
				<tr>
					Max Auto Bid $<fmt:formatNumber type = "number" maxFractionDigits = "2" minFractionDigits = "2" value = "${bid.maxbid}"/>
					<%out.println("<br>"); %>
				</tr>
		
			</c:forEach>
		
		
		</table>
		
	
	</c:if>

	<c:if test= "${not bidsuccess}">
	
	
	
	
	
	</c:if>
	
	
	
	
</c:if>




</body>
</html>