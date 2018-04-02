<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
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

<h4> My Auctions</h4>

<h5> Create Auction</h5>

<h6> Delete Account</h6>

Contact Us

</body>
</html>