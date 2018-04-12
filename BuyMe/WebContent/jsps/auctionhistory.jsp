<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Auction History</title>
</head>
<body>
<% if (!(session.getAttribute("username") == null)) { %>

	<form action="${pageContext.request.contextPath}/AuctionHistory" method="post">
	<button type="submit" name="button" value="aquery">View My Auctions</button>
	</form>



<% } %>

<c:if test="${not empty success}">


	<%-- If we are able to get history, printout  --%>
	<c:if test = "${success}">
		<text>Auction History Retrieval Success!</text>
		
		<text>Auction History: <% int numq = (int)request.getAttribute("newsize");
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
		
		<table>
		
			<c:forEach var = "auction" items = "${ahtable}">
				<tr>
					<c:out value= "${auction.posttime}"/>
				</tr>
				
				<tr>
					<c:out value= "${auction.endtime}"/>
				</tr>
				
				<tr>
				
					<c:out value= "${auction.seller}"/>
					
					<%out.println("<br>"); %>
					
				</tr>
		
			</c:forEach>
		
		
		</table>

		
		
		
		
		
		
		
	</c:if>

	<c:if test = "${not success}">
		<text> Auction History Retrieval Failed. </text>
	</c:if>




</c:if>




</body>
</html>