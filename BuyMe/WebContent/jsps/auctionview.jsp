<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>Auction ${auctionId}</title>
</head>
<body>

<h1>${title}</h1>

By ${author}

<p>

Seller: ${seller}

</p>

<p>
OpenDate: ${openDateString}

CloseDate: ${closeDateString}
</p>

<p>
Details

<br>

isbn: ${isbn}

<br>

format: ${format}

<br>

pageCount: ${pageCount}

<br>

publisher: ${publisher}

<br>

${subcategory}

<br>

<c:if test="${not empty attribute1}">
    ${attribute1}
    <br>
</c:if>

<c:if test="${not empty attribute2}">
    ${attribute2}
</c:if>

</p>






</body>
</html>