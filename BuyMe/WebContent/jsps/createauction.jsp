<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%-- Kyle created this page--%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
function selectCheckFormat(nameSelect)
{
    if(nameSelect){
    	if("physical"==nameSelect.value||"ebook"==nameSelect.value) {
    		document.getElementById("formatChoicesOptions").style.display = "inline-block";
    	}
    	else if("audiobook" == nameSelect.value) {
    		document.getElementById("formatChoicesOptions").style.display = "none";
    	}
    	else {
    		
    	}
    }
    else{
        
    }
}

function selectCheckFiction(nameSelect)
{
    if(nameSelect){
		if("fiction" == nameSelect.value){
            document.getElementById("fictionChoicesOptions").style.display = "inline-block";
        	document.getElementById("nonfictionChoicesOptions").style.display = "none";
        }
        else if("nonfiction" == nameSelect.value){
            document.getElementById("fictionChoicesOptions").style.display = "none";
            document.getElementById("nonfictionChoicesOptions").style.display = "inline-block";
        }
    }
    else{
        
    }
}

function selectCheckFictionSub(nameSelect)
{
    if(nameSelect){
		if("anthology" == nameSelect.value){
            document.getElementById("anthologyOptions").style.display = "inline-block";
        	document.getElementById("poetryOptions").style.display = "none";
        	document.getElementById("comicOptions").style.display = "none";
        }
        else if("poetry" == nameSelect.value){
        	document.getElementById("anthologyOptions").style.display = "none";
         	document.getElementById("poetryOptions").style.display = "inline-block";
         	document.getElementById("comicOptions").style.display = "none";
        }
		
        else if("comics" == nameSelect.value){
       	 	document.getElementById("anthologyOptions").style.display = "none";
        	document.getElementById("poetryOptions").style.display = "none";
        	document.getElementById("comicOptions").style.display = "inline-block";
       }
		
        else {
        	document.getElementById("anthologyOptions").style.display = "none";
        	document.getElementById("poetryOptions").style.display = "none";
        	document.getElementById("comicOptions").style.display = "none";
        }
    }
    else{
        
    }
}

function selectCheckNonFictionSub(nameSelect)
{
    if(nameSelect){
		if("biography" == nameSelect.value){
            document.getElementById("biographyOptions").style.display = "inline-block";
        	document.getElementById("magazineOptions").style.display = "none";
        }
        else if("magazine" == nameSelect.value){
        	document.getElementById("biographyOptions").style.display = "none";
        	document.getElementById("magazineOptions").style.display = "inline-block";
        }
		
        else {
        	document.getElementById("biographyOptions").style.display = "none";
        	document.getElementById("magazineOptions").style.display = "none";
        }
    }
    else{
        
    }
}


function validateForm() {
	var title=document.forms["auctionForm"]["title"].value;
	
	if(title=="") {
		alert("Please enter a title");
		return false;
	}
	
	var author=document.forms["auctionForm"]["author"].value;
	
	if(author=="") {
		alert("Please enter an author");
		return false;
	}
	
	var author=document.forms["auctionForm"]["isbn"].value;
	
	if(isbn=="") {
		alert("Please enter an isbn");
		return false;
	}
	
	var publisher=document.forms["auctionForm"]["publisher"].value;
	
	if(publisher=="") {
		alert("Please enter a publisher");
		return false;
	}
	
	var openDate=document.forms["auctionForm"]["openDate"].value;
	
	if(openDate=="") {
		alert("Please select an open date");
		return false;
	}
	
	var closeDate=document.forms["auctionForm"]["closeDate"].value;
	
	if(closeDate=="") {
		alert("Please select a close date");
		return false;
	}
	
	var openHour=parseInt(document.forms["auctionForm"]["openHour"].value,10);
	
	if(openHour<0||openHour>23||openHour=="") {
		alert("Please enter a valid open hour");
		return false;
	}
	
	var closeHour=parseInt(document.forms["auctionForm"]["closeHour"].value,10);
	
	if(closeHour<0||closeHour>23||closeHour=="") {
		alert("Please enter a valid close hour");
		return false;
	}
	
	var openMinutes=parseInt(document.forms["auctionForm"]["openMinutes"].value,10);
	
	if(openMinutes<0||openMinutes>59||openMinutes=="") {
		alert("Please enter a valid open minutes");
		return false;
	}
	
	var closeMinutes=parseInt(document.forms["auctionForm"]["closeMinutes"].value,10);
	
	if(closeMinutes<0||closeMinutes>59||closeMinutes=="") {
		alert("Please enter a valid open minutes");
		return false;
	}
	
	var reservePrice=parseFloat(document.forms["auctionForm"]["reservePrice"].value);
	
	if(reservePrice<0||reservePrice=="") {
		alert("Please enter a valid reserve price");
		return false;
	}
	
	var format=document.forms["auctionForm"]["format"].value;
	
	if(format=="physical"||format=="audiobook") {
		var numPages=document.forms["auctionForm"]["numPages"].value;
		
		if(numPages<1||numPages=="") {
			alert("Please enter a valid number of pages");
			return false;
		}
	}
	
	var fictionType=document.forms["auctionForm"]["format"].value;
	
	if(fictionType=="fiction") {
		var genre=document.forms["auctionForm"]["genre"].value;
		
		if(genre=="") {
			alert("Please enter a genre");
			return false;
		}
		
		var fictionSubType=document.forms["auctionForm"]["fictionSubType"].value;
		
		if(fictionSubType=="anthology") {
			var editors=document.forms["auctionForm"]["editors"].value;
			
			if(editors=="") {
				alert("Please enter a valid number of editors");
				return false;
			}
		}
		
		else if (fictionSubType=="poetry") {
			var poetryStyle=document.forms["auctionForm"]["poetryStyle"].value;
			
			if(poetryStyle=="") {
				alert("Please enter a valid poetry style");
				return false;
			}
		}
		
		else if (fictionSubType=="comics") {
			var volume=document.forms["auctionForm"]["volume"].value;
			var issueComic=document.forms["auctionForm"]["issueComic"].value;
			
			if(volume=="") {
				alert("Please enter a valid volume");
				return false;
			}
			
			if(issueComic=="") {
				alert("Please enter a valid issue");
				return false;
			}
		}
	}
	
	else {
		var subject=document.forms["auctionForm"]["subject"].value;
		
		if(subject=="") {
			alert("Please enter a valid subject");
			return false;
		}
			
			
		var nonfictionSubType=document.forms["auctionForm"]["nonfictionSubType"].value;
		
		if(nonfictionSubType=="biography") {
			var era = document.forms["auctionForm"]["era"].value;
			
			if(era=="") {
				alert("Please enter a valid era"); 
				return false;
			}
		}
		
		else if (nonfictionSubType=="magazine") {
			var magazineIssue=documents.forms["auctionForm"]["magazineIssue"].value;
			
			if(magazineIssue=="") {
				alert("Please enter a valid issue");
				return false;
			}
		}
	}
	
	
	return true;
	
	
}
</script>

<style>

h1 {
	text-align: center;
}

#bookDetails{
	text-align: center;
}

#formatDiv {
	text-align: center;
	display: inline-block;
}

#formatChoicesOptions {
	display: inline-block;
}

#times {
	margin-top: 10px;
	text-align: center;
}

#detailedBookDetails {
	margin-top: 10px;
	text-align: center;
}

#fictionChoicesOptions {
	text-align: center;
	display: inline-block;
}

#anthologyOptions {
	text-align: center;
	display: inline-block;
}

#poetryOptions {
	text-align: center;
	display: inline-block;
}

#comicOptions {
	text-align: center;
	display: inline-block;
}

#nonfictionChoicesOptions {
	text-align: center;
	display: inline-block;
}

#biographyOptions {
	text-align: center;
	display: inline-block;
}

#magazineOptions {
	text-align: center;
	display: inline-block;
}

#reservePriceDiv {
	margin-top: 10px;
	text-align: center;
}

#submitButton {
	margin-top: 10px;
	text-align: center;
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

<title>Create Auction</title>
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
<h1>Create Auction</h1>

<form name="auctionForm" action="${pageContext.request.contextPath}/createauction" onsubmit="return validateForm()"  method="post">
	
	<div id="bookDetails">
		<input type="text" name="title" placeholder="Title">
		
		<input type="text" name="author" placeholder="Author">
		
		<input type="text" name="isbn" placeholder="ISBN">
		
		<input type="text" name="publisher" placeholder="Publisher">
		
		<div id="formatDiv">
			<select name="format" onchange="selectCheckFormat(this);">
				<option value="physical">Physical</option>
				<option value="ebook">Ebook</option>
				<option value="audiobook">Audiobook</option>
			</select>
			
			<div id="formatChoicesOptions">
				<input type="number" name="numPages" placeholder="Number of Pages" size=10 step="1">
			</div>
		</div>
	
	</div>
	
	<div id="detailedBookDetails">
	
		<select name="fictionType" onchange="selectCheckFiction(this);">
			<option value="fiction">Fiction</option>
			<option value="nonfiction">Nonfiction</option>
		</select>
		
		<div id="fictionChoicesOptions">
			<input type="text" name="genre" placeholder="Genre">
			<select name="fictionSubType" onchange="selectCheckFictionSub(this);">
				<option value="novel">Novel</option>
				<option value="anthology">Anthology</option>
				<option value="poetry">Poetry</option>
				<option value="comics">Comics/Graphic Novels</option>
			</select>
			
			<div id= "anthologyOptions" style="display:none;">
				<input type="text" name="editors" placeholder="Editors">
			</div>
				
			<div id= "poetryOptions" style="display:none;">
				<input type="text" name="poetryStyle" placeholder="Style">
			</div>
				
			<div id= "comicOptions" style="display:none;">
				<input type="text" name="volume" placeholder="Volume">
				<input type="text" name="issueComic" placeholder="Issue">
			</div>
		</div>
		
		<div id="nonfictionChoicesOptions" style="display:none;">
			<input type="text" name="subject" placeholder="Subject">
			<select name="nonfictionSubType" onchange="selectCheckNonFictionSub(this);">
				<option value="biography">Biography</option>
				<option value="textbook">Textbook</option>
				<option value="referenceMaterial">ReferenceMaterial</option>
				<option value="magazine">Magazine</option>
			</select>
			
			<div id= "biographyOptions">
				<input type="text" name="era" placeholder="Era">
			</div>
				
			<div id= "magazineOptions" style="display:none;">
				<input type="text" name="issueMagazine" placeholder="Issue">
			</div>
		</div>
	
	</div>
	
	<div id="times">
	
		<input  type="date" name="openDate">
		
		<input  type="number" name="openHour" placeholder="Open Hour"  step="1">
		
		<input  type="number" name="openMinutes" placeholder="Open Minutes"  step="1">
		
		<input  type="date" name="closeDate">
		
		<input  type="number" name="closeHour" placeholder="Close Hour"  step="1">
		
		<input  type="number" name="closeMinutes" placeholder="Close Minutes"  step="1">
	
	</div>
	
	<div id="reservePriceDiv">
		<input  type="number" name="reservePrice" placeholder="Reserve Price $"  step="0.01">
	</div>
	
	<div id="submitButton">
		<button type="submit" name="button" value="button1">Submit</button>
	</div>
</form>

</body>
</html>