<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style>
h1 {text-align:center;}
h2 {text-align:left;}
</style>

<title>Forum Page</title>
</head>
<body>


<h1> Welcome to the BuyMe Forum!</h1>
<br>
<br>
<h3> Search a query: </h3>
<form action="${pageContext.request.contextPath}/Forum" id="searchform" method="post">
<input type="text" name="keyword" placeholder="Keyword 1" size=50>
<input type="text" name="keyword2" placeholder="Keyword 2" size=50>
<button type="submit" name="button" value="squery">Submit</button>




</form>
<br>
<br>

<c:if test="${not empty search}" >
	<c:if test="${search}" >
		<text>Query Search returned <% int numq = (int)request.getAttribute("newsize");
										if (numq > 1)
										{
											out.print(numq + " queries\n");
										}
										else if (numq == 0)
										{
											out.print("0 queries\n");
										}
										else
										{
											out.print("1 query\n");
										}
											%> </text>
		<br>									
											
											
		<table>

			<c:forEach var="rowData" items="${qtable}">
			    <tr>
			        <c:forEach var="cellData" items="${rowData}" >
			            <c:out value= "${cellData}"/><br />                 
			        </c:forEach>
			    </tr>
			    <br>
			</c:forEach>
			
		</table>
		
		
	</c:if>
	
	<c:if test="${not search}" >
		<text>Query Search Failed </text>
	</c:if>
	
</c:if>


<h2> Enter a new query here: </h2>
<form action="${pageContext.request.contextPath}/Forum" id="usrform" method="post">
  Heading: <input type="text" name="heading">
  <button type="submit" name="button" value="qsubmit">Submit</button>
	<br>
	<textarea rows="4" cols="50" name="query" form="qform"> </textarea>
</form>

<c:if test="${not empty success}" >
	<c:if test="${success}" >
		<text>Query Submission Successful! </text>
	</c:if>
	
	<c:if test="${not success}" >
		<c:if test="${not body}">
			<text> Query must have a body</text>
		</c:if>
		<text> Query Submission Failed.</text>
	</c:if>
	
</c:if>



</body>
</html>