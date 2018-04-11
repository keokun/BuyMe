<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script>
function selectCheckAutobidding(nameSelect) {
	if(nameSelect) {
		if("autobidding"==nameSelect.value) {
			document.getElementById("autoBidDiv").style.display = "block";
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


<title>Auction ${auctionId}</title>
</head>
<body>

<h1>${title}</h1>

<div id="author">
	By ${author}
</div>

<div id="seller">
	<p>
	
	Seller: ${seller}
	
	</p>
</div>

<div id="dates">
	<p>
	OpenDate: ${openDateString}
	
	<br>
	
	CloseDate: ${closeDateString}
	</p>
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
			
			PageCount: ${pageCount}
			
		</c:if>
		
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

</div>

<c:if test="${active == 'active'}">
	<div id="maxBidDetails" >
		<p>
			<c:if test="${not empty maxBidUsername}">
				<tr>
					<td>
						Max Bid: 
					</td>
					<td>
						${maxBidUsername}
					</td>
					
					<td>
						${maxBid}
					</td>
					
					<td>
						${maxBidDate}
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
			<input  type="number" name="bidAmount" placeholder="Bid Amount $"  step="0.01">
			
			<select name="Autobidding" onChange="selectCheckAutobidding(this)">
				<option value="autobidding">Autobidding</option>
				<option value="manual">Manual</option>
			</select>
			
			<div id="autoBidDiv">
				<input  type="number" name="autoBidAmount" placeholder="Max Autobid Amount $"  step="0.01">
			</div>
			
			<button type="submit" name="button" value="button1">Submit</button>
		
		</form>
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