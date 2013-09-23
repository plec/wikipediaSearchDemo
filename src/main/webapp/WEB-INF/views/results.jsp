<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>wikipedia</title>
<!-- Bootstrap -->
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/bootstrap/css/docs.css" rel="stylesheet">
<link href="resources/bootstrap/css/pygments-manni.css" rel="stylesheet">
<script src="resources/jquery.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<style type="text/css">
em {
background-color: yellow;
color: maroon;
font-weight: bold;
}
</style>
</head>
<body>
	<div class="container">
		<h2>Recherche dans les articles wikip�dia</h2>
		<div class="hero-unit" style="width: 900px; padding: 8px 0;">
			<form action="search" method="post">
				<input type="text" class="search-query" name="query"
					placeholder="Recherche">
				<input type="hidden" name="page" value="0"/>
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
							<li class="nav-header">R�sultat - recherche :${searchQuery} - total match :${totalResults} articles - request time : ${requestTime}ms</li>
						</ul>
      					<div class="list-group">
							<c:forEach items="${searchResult}" var="result2" varStatus="status2">
								<a href="${result2.links}" target="wikipedia" class="list-group-item active">
									<h4 class="list-group-item-heading">${result2.title}</h4>
								</a>
								<c:forEach items="${result2.highlight}" var="highlight" varStatus="status3">
									<p  class="list-group-item-text">[...] ${highlight}[...] </p>
								</c:forEach>
							</c:forEach>
						</div>
					</div>
					<ul class="nav nav-pills">
						<c:forEach items="${pages.page}" var="page" varStatus="statuspages">
							<li><a href="#">${page}</a></li>
						</c:forEach>
					</ul>
				</div>
			</c:when>
		</c:choose>
	</div>
</body>
</html>
