<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%-- Kyle created this page--%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script>
function selectCheckAutobidding(nameSelect) {
	if(nameSelect) {
		if("autobidding"==nameSelect.value) {
			document.getElementById("autoBidDiv").style.display = "inline-block";
		}
		
		else {
			document.getElementById("autoBidDiv").style.display = "none";
		}
	}
}


function validateForm() {
	var bidAmount=parseFloat(document.forms["bidForm"]["bidAmount"].value);
	
	if(bidAmount<'${minBid}'||bidAmount=="") {
		alert("Please enter a valid bid amount");
		return false;
	}
	
	var autoBidding=document.forms["bidForm"]["Autobidding"].value;
	
	if(autoBidding=="autobidding") {
		var autoBidAmount=parseFloat(document.forms["bidForm"]["autoBidAmount"].value);
		
		if(autoBidAmount<(bidAmount+0.01)||autoBidAmount=="") {
			alert("Please enter a valid autoBid amount");
			return false;
		}
	}
	
	return true;
	
	
}


</script>

<style>

h1 {
	text-align: center;
}

#author {
	text-align: center;
}

#seller {
	margin-top: 5px;
	text-align: center;
}

#dates {
	margin-top: 10px;
	text-align: center;
}

#details {
	margin-top: 10px;
	text-align: center;
}

#itemSold {
	margin-top: 10px;
	text-align: center;
}

#itemNotSold {
	margin-top: 10px;
	text-align: center;
}

#maxBidDetails {
	margin-top: 10px;
	text-align: center;
}

#bid {
	margin-top: 10px;
	text-align: center;
}

#bidAmountDiv {
	display: inline-block;
}

#autoBiddingDiv {
	display: inline-block;
}

#autoBidDiv {
	display: inline-block;
}


#auctionNotBegun {
	margin-top: 10px;
	text-align: center;
}

#submitButton {
	margin-top: 10px;
	text-align: center;
}

#amSellerDiv {
	margin-top: 10px;
	text-align: center;
}

table.center {
    margin-left:auto; 
    margin-right:auto;
    border-collapse: separate;
  	border-spacing: 25px 0;
  }
  
  
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

li a:hover {
    background-color: #111;
}

</style>

<title>Auction ${auctionId}</title>
<link rel="import" href="navBar.html">
</head>
<body>
<div id="navBar">
<ul>
	<li><a href="${pageContext.request.contextPath}/logout">Logout</a> </li>
	<li><a href="${pageContext.request.contextPath}/createauction">Create Auction</a></li>
	<li><a href="${pageContext.request.contextPath}/browse">Browse</a></li>
	<li><a href="${pageContext.request.contextPath}/Forum">Forum</a></li>
	<li><a href="${pageContext.request.contextPath}/home">Home</a></li>
</ul>
</div>

<h1>${title}</h1>

<div id="author">
	By ${author}
</div>

<div id="seller">
	
	Sold By: <a href="${pageContext.request.contextPath}/AuctionHistory?user=${seller}">${seller}</a>

</div>

<div id="dates">
	<table class="center">
		<tr>
			<td>
				Open Date: <fmt:formatDate type = "both" dateStyle = "long" timeStyle = "medium" value = "${openDate}" />
			</td>
			
			<td>
			Close Date: <fmt:formatDate type = "both" dateStyle = "long" timeStyle = "medium" value = "${closeDate}" />
			</td>
		</tr>
	</table>
</div>

<div id="details">
	<p>
		Details
		
		<br>
		
		Isbn: ${isbn}
		
		<br>
		
		Format: ${format}
		
		<c:if test="${not empty pageCount}">
			
			<br>
			
			Page Count: ${pageCount}
			
		</c:if>
		
		<br>
		
		Publisher: ${publisher}
		
		<br>
		
		Sub-category: ${subcategory}
		
		<br>
		
		<c:if test="${fictionType}">
				Genre: ${genre}
		</c:if>
		
		<c:if test="${not fictionType}">
				Subject: ${genre}
		</c:if>
		
		<br>
		
		<c:if test="${not empty attribute1}">
			<c:if test="${subcategory == 'Comics'}">
				Volume: ${attribute1}
			</c:if>
			<c:if test="${subcategory == 'Poetry'}">
				Style: ${attribute1}
			</c:if>
			<c:if test="${subcategory == 'Anthology'}">
				Editors: ${attribute1}
			</c:if>
		    <c:if test="${subcategory == 'Biography'}">
				Era: ${attribute1}
			</c:if>
			<c:if test="${subcategory == 'Magazine'}">
				Issue: ${attribute1}
			</c:if>
		    <br>
		</c:if>
		
		<c:if test="${not empty attribute2}">
			<c:if test="${subcategory == 'Comics'}">
				Issue: ${attribute1}
			</c:if>
		</c:if>
	
	</p>
	
	<p>
		<a href="${pageContext.request.contextPath}/BidHistory?auctionid=${auctionId}">Bid History</a>
	</p>

</div>

<c:if test="${active == 'active' && not amSeller}">
	<div id="maxBidDetails" >
		<p>
			<c:if test="${not empty maxBidUsername}">
				<tr>
					<td>
						Max Bid: 
					</td>
					<td>
						By <a href="${pageContext.request.contextPath}/AuctionHistory?user=${maxBidUsername}">${maxBidUsername}</a> for
					</td>
					
					<td>
						$<fmt:formatNumber type = "number" maxFractionDigits = "2" minFractionDigits = "2" value = "${maxBid}" />
					</td>
					
					<td>
						on <fmt:formatDate type = "both" dateStyle = "long" timeStyle = "medium" value = "${maxBidDate}" />
					</td>
				</tr>
			</c:if>
			<c:if test="${empty maxBidUsername}">
				No bids submitted
			</c:if>
		</p>
	</div>
	
		<div id="bid">
			<form name="bidForm" action="${pageContext.request.contextPath}/auctionview?auctionid=${param.auctionid}" onsubmit="return validateForm()" method="post">
				<div id="bidAmountDiv">
					<input  type="number" name="bidAmount" placeholder="Bid Amount $"  step="0.01">
				</div>
				
				<div id="autoBiddingDiv">
					<select name="Autobidding" onChange="selectCheckAutobidding(this)">
						<option value="autobidding">Autobidding</option>
						<option value="manual">Manual</option>
					</select>
				</div>
				
				<div id="autoBidDiv">
					<input  type="number" name="autoBidAmount" placeholder="Max Autobid Amount $"  step="0.01">
				</div>
				
				<div id="submitButton">
					<button type="submit" name="button" value="button1">Submit</button>
				</div>
			
			</form>
		</div>
	
</c:if>

<c:if test="${active == 'active' && amSeller}">
	<div id="maxBidDetails" >
		<p>
			<c:if test="${not empty maxBidUsername}">
				<tr>
					<td>
						Max Bid: 
					</td>
					<td>
						By ${maxBidUsername} for
					</td>
					
					<td>
						$<fmt:formatNumber type = "number" maxFractionDigits = "2" minFractionDigits = "2" value = "${maxBid}" />
					</td>
					
					<td>
						on <fmt:formatDate type = "both" dateStyle = "long" timeStyle = "medium" value = "${maxBidDate}" />
					</td>
				</tr>
			</c:if>
			<c:if test="${empty maxBidUsername}">
				No bids submitted
			</c:if>
		</p>
	</div>
	
	<div id=amSellerDiv>
		Your are the seller of this item and cannot bid.
	</div>	
	
</c:if>



<div id="auctionNotBegun">
	<c:if test="${active == 'before'}">
		<p>
			The Auction has not yet begun.
		</p>
	</c:if>
</div>

<div id="itemSold">
	<c:if test="${(active == 'after') && sold}">
		The item was sold to ${maxBidUsername} for $${maxBid}
	</c:if>
</div>

<div id="itemNotSold">
	<c:if test="${(active == 'after') && (not sold)}">
		The item was not sold because no bid met the reserve price of $${reservePrice}
	</c:if>
</div>






</body>
</html>