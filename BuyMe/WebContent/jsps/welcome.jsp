<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%--Everyone contributed equally to this page --%>
<head>
<style>
h1 {text-align:center;}
h2 {text-align:center;}
h3 {text-align: left;}
h4 {text-align: right;}
h5 {text-align: right;}
h6 {text-align: right;}



</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to BuyMe!</title>
</head>
<body>


<h1> Welcome to BuyMe <%out.println(request.getAttribute("firstname"));%>!</h1>

<h2> Let's get started.</h2>

<h3> Inbox </h3>

<% if (!(session.getAttribute("username") == null)) { %>

	<form action="${pageContext.request.contextPath}/AuctionHistory" method="post">
	<button type="submit" name="button" value="auctionH">My Auctions</button>
	</form>
	
	
	<form action="${pageContext.request.contextPath}/BidHistory" method="post">
	<button type="submit" name="button" value="bidH">My Bids</button>
	</form>
<% } %>


<h5> Create Auction</h5>

<h6> Delete Account</h6>

<a href="${pageContext.request.contextPath}/home">Logout</a>
<br>
<br>
<a href="${pageContext.request.contextPath}/Forum">Questions?</a>
<br>
</body>
</html>