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


<c:if test="${not empty success}">


	<%-- If we are able to get history, printout  --%>
	<c:if test = "${success}">
		<text>Auction History Retrieval Success!</text>
		<br>
		
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
		<br>
		<table>
		
			<c:forEach var = "auction" items = "${ahtable}">
				<tr>
					<text>From: </text><c:out value= "${auction.posttime}" />
					<%out.println("<br>"); %>
				</tr>
				
				<tr>
					To: <c:out value= "${auction.endtime}"/>
					<%out.println("<br>"); %>
				</tr>
				
				<tr>
					<c:out value= "${auction.book}"/>
					<%out.println("<br>"); %>
				</tr>
				<tr>
				
					<c:out value= "${auction.seller}"/>
					
					<%out.println("<br><br>"); %>
					
				</tr>
		
			</c:forEach>
		
		
		</table>

		
		
		
		
		
		
		
	</c:if>

	<c:if test = "${not success}">
		<text> Auction History Retrieval Failed. </text>
	</c:if>




</c:if>

<c:if test= "${not empty bidsuccess }">
	
	<c:if test= "${bidsuccess }">
	<text>Bid History Retrieval Success!</text>
		<br>
		
		<text>Bid History <% int numq = (int)request.getAttribute("newsize");
										if (numq > 1)
										{
											out.print(numq + " bids\n");
										}
										else if (numq == 0)
										{
											out.print("0 bids\n");
										}
										else
										{
											out.print("1 bid\n");
										}
											%> </text>
		
		<br>									
		<br>
		<table>
		
			<c:forEach var = "bid" items = "${bidtable}">
				<tr>
					<text>From: </text><c:out value= "${bid.time}" />
					<%out.println("<br>"); %>
				</tr>
				
				<tr>
					To: <c:out value= "${bid.amount}"/>
					<%out.println("<br>"); %>
				</tr>
				
				<tr>
					<c:out value= "${bid.maxbid}"/>
					<%out.println("<br>"); %>
				</tr>
				
				<tr>
					<c:out value= "${bid.book}"/>
					<%out.println("<br>"); %>
				</tr>
				
				<tr>
				
					<c:out value= "${bid.seller}"/>
					
					<%out.println("<br><br>"); %>
					
				</tr>
		
			</c:forEach>
		
		
		</table>
		
	
	</c:if>

	<c:if test= "${not bidsuccess}">
	
	
	
	
	
	</c:if>
	
	
	
	
</c:if>




</body>
</html>