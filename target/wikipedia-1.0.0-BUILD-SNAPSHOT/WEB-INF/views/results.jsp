<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>wikipedia</title>
<!-- Bootstrap -->
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<script src="resources/jquery.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<h2>Recherche dans les articles wikipédia</h2>
		<div class="hero-unit">
			<form action="search" method="post">
				<input type="text" class="search-query" name="query"
					placeholder="Recherche">
				<button type="submit" class="btn btn-primary">
					<i class="icon-search icon-white"></i>
				</button>
			</form>
		</div>
		<c:choose>
			<c:when test="${not empty searchResult}">
				<div class="sidebar-nav">
					<div class="well" style="width: 900px; padding: 8px 0;">
						<ul class="nav nav-list">
							<li class="nav-header">Résultat</li>
							<c:forEach items="${searchResult}" var="result"
								varStatus="status">
								<li><a href="${result.links[0]}"
									target="wikipedia">${result.title} </a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</c:when>
		</c:choose>
	</div>
</body>
</html>
