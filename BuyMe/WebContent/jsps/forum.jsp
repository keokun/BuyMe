<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%-- Josh created this page--%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style>
h1 {text-align:center;}
h2 {text-align:left;}
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

<title>Forum Page</title>
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

<h1>BuyMe Forum</h1>
<br>
<br>

<% if (session.getAttribute("username") == null)
	{ %>
	<text> Please Login again. </text>
	<br>
	
	<a href="${pageContext.request.contextPath}/home">Home</a>
	
		
		
<% } %>
<% if (!(session.getAttribute("username") == null)) { %>

<form action="${pageContext.request.contextPath}/Forum" id="searchform" method="post">
<button type="submit" name="button" value="aquery">See All Queries</button>
</form>

<c:if test="${all}">
	<br>
	<i><text><% int numq = (int)request.getAttribute("newsize");
										if (numq > 1)
										{
											out.print(numq + " queries in total<br>");
										}
										else if (numq == 0)
										{
											out.print("0 queries\n");
										}
										else
										{
											out.print("1 query in total\n");
										}
											%> </text></i>
		<br>									
	
	<table>

			<c:forEach var="ForumItem" items="${forumList}">
			    <tr>
			    	<b><c:out value= "${ForumItem.name}"/></b><br />
			    </tr>
			    	
		    	<tr>
		    		<c:out value= "${ForumItem.content}"/><br />
		    	</tr>
			    	
			    	
			    	<% if (!session.getAttribute("type").equals("regular"))
					{ %>
						<tr>	
								<c:out value= "${ForumItem.tid}"/><br />
								<%out.println("<br>"); %>
						</tr>
					<%} %>
				<tr><br>
				</tr>
							
			</c:forEach>
			
			
		</table>
	

</c:if>

<% if (!session.getAttribute("type").equals("regular")) { %>
	<h2> Delete a query </h2>
	<form action="${pageContext.request.contextPath}/Forum" method="post">
	<input type="text" name="tid" placeholder="tid(threadid of query)" size=50>
	<button type="submit" name="button" value="del">Submit</button>
	</form>		
	
	<c:if test= "${not empty delete }">
	
		<c:if test="${delete}">
			<text> Delete of Thread Successful </text>
		</c:if>
		
		<c:if test=	"${not delete}">
			<text> Delete of Thread Failure
		</c:if>
	
	</c:if>
	<br>
	
	
	<h2> Answer a query </h2>
	<form action="${pageContext.request.contextPath}/Forum" method="post">
		TID of Query: <input type="text" name="tidquery">
		<br>
		<textarea rows="4" cols="50" name="ansquery" form="qform"> </textarea>
		<br>
		<button type="submit" name="button" value="asubmit">Submit</button>
	</form>		
	
	<c:if test="${not empty ansdone }">
		
		<c:if test= "${ansdone}">
			<text> Query answer success! </text>
		</c:if>
		
		<c:if test="${not ansdone }">
			<text> Unable to answer query: Query does not exist<text>
		</c:if>
		
	</c:if>
					 
<% } %>	

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

			<c:forEach var="ForumItem" items="${forumList}">
			    <tr>
			    	<b><c:out value= "${ForumItem.name}"/></b>
			    </tr>
			    	
		    	<tr>
		    		<c:out value= "${ForumItem.content}" />
		    	</tr>
			    	
			    <%--How do i get a newline here!!!! --%>
			    	
		    	<% if (!session.getAttribute("type").equals("regular"))
				{ %>
					<tr>	
							<c:out value= "${ForumItem.tid}"/>
							<%out.println("<br>"); %>
					</tr>
				<%} 
		    	
		    	else{%>
					<tr> <td> <br> </td> </tr>
					
				<% }%>
				
			
			    
							
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
  <button type="submit" name="button" value="qsubmit">Submit</button><br>
	<br>
	<textarea rows="4" cols="50" name="query"> </textarea>
</form>

<c:if test="${not empty success}" >
	<c:if test="${success}" >
		<text>Query Submission Successful! </text>
	</c:if>
	
	<c:if test="${not success}" >
		<text> Query Submission Failed.</text>
		
	</c:if>
	
</c:if>


<br>
<br>
<br>

	
		
		
<% } %>		
		
		
		
<br>
<br>		
</body>
</html>