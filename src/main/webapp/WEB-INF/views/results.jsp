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
<style type="text/css">
em {
background-color: yellow;
color: maroon;
font-weight: bold;
}
p {
font-size:11px;
margin-bottom:10px;
}
h4 {
margin-bottom: 1px;
margin-top: 20px;
}
select {
margin-bottom: 0px;
width:150px;
font-size:12px;
padding-top:1px;
padding-bottom:1px;
height:20px;
}
</style>

<script src="resources/jquery.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<script>
function reloadPrevious() {
	console.log("reload !");
	$("#query").val("${searchQuery}");
}
</script>

</head>
<body>
	<div class="container">
		<h2>Recherche dans les articles wikipédia</h2>
		<div class="hero-unit" style="width: 900px; padding: 8px 0;">
			<form action="search" method="post">
				<input type="text" class="search-query" name="query" id="query"
					placeholder="Recherche">
				 <select class="search-query" name="searchEngine">
				    <option value="SOLR">SolR</option>
				    <option value="ES">Elastic Search</option>
				  </select>
				<input type="hidden" name="page" value="0"/>
				<button type="submit" class="btn btn-primary">
					<i class="icon-search icon-white"></i>
				</button>
				<button  onclick="javascript:reloadPrevious()" type="button" class="btn btn-primary">
					<i class="icon-refresh icon-white"></i>
				</button>
			</form>
		</div>
		<c:choose>
			<c:when test="${not empty searchResult}">
				<div class="sidebar-nav">
					<div class="well" style="width: 900px; padding: 8px 0;">
						<ul class="nav nav-list">
							<li class="nav-header">Résultat - recherche :${searchQuery} - total match :${totalResults} articles - request time : ${requestTime}ms - engine :${engine}</li>
						</ul>
      					<div class="list-group">
							<c:forEach items="${searchResult}" var="result2" varStatus="status2">
								<a href="${result2.links}" target="wikipedia" class="list-group-item active">
									<h4 class="list-group-item-heading">${result2.title}</h4>
								</a>
								<p  class="list-group-item-text">
								<c:forEach items="${result2.highlight}" var="highlight" varStatus="status3">
									[...] ${highlight}[...] 
								</c:forEach>
									</p>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:when>
		</c:choose>
		<c:choose>
							<c:when test="${not empty pages.page}">
							<div class="well" style="width: 900px; padding: 8px 0;">
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
