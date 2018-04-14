<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%-- Kyle created this page --%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Inbox</title>
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
	<li><a href="${pageContext.request.contextPath}/inbox">Inbox</a></li>
	<li><a href="${pageContext.request.contextPath}/home">Home</a></li>
	
</ul>

</div>
<h1>Inbox for ${username}</h1>

<p>
Number of Messages: ${numMessages}
</p>

<br>


<c:forEach var = "message" items = "${messageList}">
Sender: ${message.sender}
<br>
Receiver: ${message.receiver}
<br>
Send Time: ${message.sendtime}
<br>
<p>
Contents: ${message.contents}
</p>

<br>
<br>
				
		
</c:forEach>

</body>
</html>