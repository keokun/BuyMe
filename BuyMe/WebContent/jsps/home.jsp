<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%--Everyone contributed equally to this page --%>
<head>
<style>
h1 {text-align:center;}
h2 {text-align:center;}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to BuyMe!</title>
</head>
<body>


<h1> Welcome to BuyMe! </h1>

<br>
Not registered? Get started.
<br>
<a href="${pageContext.request.contextPath}/createaccount">Create an Account</a>
<br>
<br>
Already have an account?
<br>
<a href="${pageContext.request.contextPath}/login">Log in.</a>
<br>


</body>
</html>