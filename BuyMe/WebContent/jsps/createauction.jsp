<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
function selectCheckFormat(nameSelect)
{
    if(nameSelect){
    	if("physical"==nameSelect.value||"ebook"==nameSelect.value) {
    		document.getElementById("formatChoicesOptions").style.display = "block";
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
            document.getElementById("fictionChoicesOptions").style.display = "block";
        	document.getElementById("nonfictionChoicesOptions").style.display = "none";
        }
        else if("nonfiction" == nameSelect.value){
            document.getElementById("fictionChoicesOptions").style.display = "none";
            document.getElementById("nonfictionChoicesOptions").style.display = "block";
        }
    }
    else{
        
    }
}

function selectCheckFictionSub(nameSelect)
{
    if(nameSelect){
		if("anthology" == nameSelect.value){
            document.getElementById("anthologyOptions").style.display = "block";
        	document.getElementById("poetryOptions").style.display = "none";
        	document.getElementById("comicOptions").style.display = "none";
        }
        else if("poetry" == nameSelect.value){
        	document.getElementById("anthologyOptions").style.display = "none";
         	document.getElementById("poetryOptions").style.display = "block";
         	document.getElementById("comicOptions").style.display = "none";
        }
		
        else if("comics" == nameSelect.value){
       	 	document.getElementById("anthologyOptions").style.display = "none";
        	document.getElementById("poetryOptions").style.display = "none";
        	document.getElementById("comicOptions").style.display = "block";
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
            document.getElementById("biographyOptions").style.display = "block";
        	document.getElementById("magazineOptions").style.display = "none";
        }
        else if("magazine" == nameSelect.value){
        	document.getElementById("biographyOptions").style.display = "none";
        	document.getElementById("magazineOptions").style.display = "block";
        }
		
        else {
        	document.getElementById("biographyOptions").style.display = "none";
        	document.getElementById("magazineOptions").style.display = "none";
        }
    }
    else{
        
    }
}
</script>


<title>Create Auction</title>
</head>
<body>

<h1>Create Auction</h1>

<form action="${pageContext.request.contextPath}/createauction" method="post">
	

	<input type="text" name="title" placeholder="Title" required=true>
	
	<input type="text" name="author" placeholder="Author" required=true>
	
	<input type="text" name="isbn" placeholder="ISBN" required=true>
	
	<input type="text" name="publisher" placeholder="Publisher" required=true>
	
	<input  type="date" name="openDate" required=true;>
	
	<input  type="number" name="openHour" placeholder="Open Hour"  step="1" required=true; min="0" max=23>
	
	<input  type="number" name="openMinutes" placeholder="Open Minutes"  step="1" required=true; min="0" max=59>
	
	<input  type="date" name="closeDate" requires=true;>
	
	<input  type="number" name="closeHour" placeholder="Close Hour"  step="1" required=true; min="0" max=23>
	
	<input  type="number" name="closeMinutes" placeholder="Close Minutes"  step="1" required=true; min="0" max=59>
	
	<input  type="number" name="reservePrice" placeholder="Reserve Price $"  step="0.01" required=true; min="0">
	
	<select name="format" onchange="selectCheckFormat(this);">
		<option value="physical">Physical</option>
		<option value="ebook">Ebook</option>
		<option value="audiobook">Audiobook</option>
	</select>
	
	<div id="formatChoicesOptions">
		<input type="number" name="numPages" placeholder="Number of Pages" size=10 min="1" step="1">
	</div>
	
	
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
	
	<button type="submit" name="button" value="button1">Submit</button>
</form>

</body>
</html>