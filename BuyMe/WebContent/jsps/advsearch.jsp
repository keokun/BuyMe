<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%--Kristen created this page --%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Advanced Search</title>
<style>
h1 {text-align:center;}
ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: #333;
}
li { float: right; }
li a {
    display: block;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}
li a:hover { background-color: #111; }
a { text-decoration: none; }
</style>
<script>
function selectCheckFormat(nameSelect) {
    if(nameSelect){
    	if(nameSelect.value == "physical" || nameSelect.value == "ebook" || nameSelect.value == "ns") {
    		document.getElementById("pgcount").style.display = "block";
    	}
    	else if(nameSelect.value == "audiobook") {
    		document.getElementById("pgcount").style.display = "none";
    	}
    }
}

function selectCheckFiction(nameSelect) {
    if(nameSelect) {
		if(nameSelect.value == "fic") {
            document.getElementById("ficOptions").style.display = "block";
        	document.getElementById("nonficOptions").style.display = "none";
        } else if(nameSelect.value == "nonfic") {
            document.getElementById("ficOptions").style.display = "none";
            document.getElementById("nonficOptions").style.display = "block";
        } else {
        	document.getElementById("ficOptions").style.display = "none";
            document.getElementById("nonficOptions").style.display = "none";
        }
    }
}

function selectCheckFictionSub(nameSelect) {
    if(nameSelect) {
		if(nameSelect.value == "Anthology") {
            document.getElementById("anthoOptions").style.display = "block";
        	document.getElementById("poetryOptions").style.display = "none";
        	document.getElementById("comicOptions").style.display = "none";
        } else if(nameSelect.value == "Poetry") {
        	document.getElementById("anthoOptions").style.display = "none";
         	document.getElementById("poetryOptions").style.display = "block";
         	document.getElementById("comicOptions").style.display = "none";
        } else if(nameSelect.value == "Comic/Graphic Novel") {
       	 	document.getElementById("anthoOptions").style.display = "none";
        	document.getElementById("poetryOptions").style.display = "none";
        	document.getElementById("comicOptions").style.display = "block";
       } else {
        	document.getElementById("anthoOptions").style.display = "none";
        	document.getElementById("poetryOptions").style.display = "none";
        	document.getElementById("comicOptions").style.display = "none";
        }
    }
}

function selectCheckNonFictionSub(nameSelect) {
    if(nameSelect) {
		if(nameSelect.value == "Biography"){
            document.getElementById("bioOptions").style.display = "block";
        	document.getElementById("magOptions").style.display = "none";
        } else if(nameSelect.value == "Magazine") {
        	document.getElementById("bioOptions").style.display = "none";
        	document.getElementById("magOptions").style.display = "block";
        } else {
        	document.getElementById("bioOptions").style.display = "none";
        	document.getElementById("magOptions").style.display = "none";
        }
    }
}
</script>
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
<h1>Advanced Search</h1>
<b><a href="${pageContext.request.contextPath}/browse">Back to Browse</a></b><br>
<br><br>

<form method="get" action="${pageContext.request.contextPath}/advsearch">
<i>ISBN:</i> <input type="text" name="isbn" id="isbn"><br>
<br>
<i>Title:</i> <input type="text" name="title" id="title"><br>
<br>
<i>Author:</i> <input type="text" name="author" id="author"><br>
<br>
<i>Publisher:</i> <input type="text" name="publisher" id="publisher"><br>
<br>

<i>Format:</i> <select name="format" onchange="selectCheckFormat(this);">
	<option value="ns">Not Specified</option>
	<option value="physical">Physical</option>
	<option value="ebook">E-book</option>
	<option value="audiobook">Audiobook</option>
</select><br>
<br>

<div id="pgcount">
<i>Minimum Page Count:</i> <input type="number" name="minpg" min="1" step="1"> <i>Maximum Page Count:</i> <input type="number" name="maxpg" min="1" step="1"><br>
<br></div>
<i>Minimum Price:</i> $<input type="text" name="minpr" id="minpr"> <i>Maximum Price:</i> $<input type="text" name="maxpr" id="maxpr"><br>
<br>

<i>Fiction or Nonfiction:</i> <select name="ficType" onchange="selectCheckFiction(this);">
	<option value="ns">Not Specified</option>
	<option value="fic">Fiction</option>
	<option value="nonfic">Nonfiction</option>
</select><br><br>

<div id="ficOptions" style="display:none;">
	<i>Genre:</i> <input type="text" name="genre"><br>
	<br>
	<i>Subcategory:</i> <select name="ficSubcat" onchange="selectCheckFictionSub(this);">
		<option value="ns">Not Specified</option>
		<option value="Novel">Novel</option>
		<option value="Anthology">Anthology</option>
		<option value="Poetry">Poetry</option>
		<option value="Comic/Graphic Novel">Comic/Graphic Novel</option>
	</select><br>
	<br>
	<div id= "anthoOptions" style="display:none;">
		<i>Editors:</i> <input type="text" name="editors"><br>
		<br>
	</div>	
	<div id= "poetryOptions" style="display:none;">
		<i>Style:</i> <input type="text" name="pStyle"><br>
		<br>
	</div>
	<div id= "comicOptions" style="display:none;">
		<i>Volume:</i> <input type="text" name="volume"><br>
		<br>
		<i>Issue:</i> <input type="text" name="issue"><br>
		<br>
	</div>
</div>
<div id="nonficOptions" style="display:none;">
	<i>Subject:</i> <input type="text" name="subject"><br>
	<br>
	<i>Subcategory:</i> <select name="nonficSubcat" onchange="selectCheckNonFictionSub(this);">
		<option value="ns">Not Specified</option>
		<option value="Biography">Biography</option>
		<option value="Textbook">Textbook</option>
		<option value="Reference Material">Reference Material</option>
		<option value="Magazine">Magazine</option>
	</select><br>
	<br>	
	<div id= "bioOptions" style="display:none;">
		<i>Era:</i> <input type="text" name="era"><br>
		<br>
	</div>
	<div id= "magOptions" style="display:none;">
		<i>Issue:</i> <input type="text" name="issueMagazine"><br>
		<br>
	</div>
</div>
<input type="submit" name="Search" value="Search">  <input type="submit" name="Alert" value="Set an Alert">
</form>
<br>
<br>
${alert}

</body>
</html>