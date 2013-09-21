<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>wikipedia</title>
</head>
<body>
<h1>
	Recherche
</h1>
<div id="container">
<form action="search" method="post">
<input type="text" name="query" size="100"/>
<input type="submit" value="Rechercher"/>
</form>
</div>

<h1>
	Resultat de recherche
</h1>
 <ul>
 <c:forEach items="${searchResult}" var="result" varStatus="status">
 <li><a href="${result.links[0]}" target="wikipedia">${result.title} </a></li>
 </c:forEach>
 </ul>
</body>
</html>
