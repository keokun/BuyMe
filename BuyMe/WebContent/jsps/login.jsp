<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%--Everyone contributed equally to this page --%>
<head>
<style>
h1 {text-align:center;}
h2 {text-align:center;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<style>
.main {
    margin-left: 0px; /* Same width as the sidebar + left position in px */
    font-size: 16px; /* Increased text to enable scrolling */
    padding: 0px 10px;
    text-align: center;
}

</style>
<title>Buy Me</title>
</head>

<a href="${pageContext.request.contextPath}/home">Back to home</a>

<h1>Login</h1>
<br>
<br>
<h2>Please enter your account information below:</h2>
<br>
<br>



<div class="main">

<form action="${pageContext.request.contextPath}/" method="post">
	<input type="text" name="username" placeholder="Username" size=50> 
	<br>
	<input type="password" name="password" placeholder="Password" size=50> 
	<br>
	<button type="submit" name="button" value="button1">Submit</button>
</form>



<c:if test="${not empty success}" >
	<br>
	<c:if test="${success}" >
		<text>Login Succeeded </text>
	</c:if>
	
	<c:if test="${not success}" >
		<text>Login Failed </text>
	</c:if>
	
</c:if>
<br>
<br>
<a href="${pageContext.request.contextPath}/createaccount">Create An Account</a>

</div>


</body>
</html>