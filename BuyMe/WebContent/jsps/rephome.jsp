<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<a href="${pageContext.request.contextPath}/Forum">Forum</a>

<br>
<br>

<a href="${pageContext.request.contextPath}/home">Logout</a>

</body>
</html>