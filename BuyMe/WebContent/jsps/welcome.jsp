<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
h1 {text-align:center;}
h2 {text-align:center;}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to BuyMe!</title>
</head>
<body>


<h1> Welcome to BuyMe <%out.println(request.getAttribute("firstname"));%>!</h1>

<h2> Let's get started.</h2>


</body>
</html>