<%--
  Created by IntelliJ IDEA.
  User: hanna
  Date: 09.12.2016
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/custom-functions.tld" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demonstrator &mdash; Search</title>
    <meta charset="utf-8">
    <%@ include file="../partials/head.jsp" %>
</head>
<body class="search">
<%@ include file="../partials/navigation.jsp" %>
<div class="main-header">
    <div class="jumbotron">
        <h1 class="display-4">Suche</h1>
        <p class="lead">Suchergebnisse für ${term}</p>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col">
            <div class="card advanced-search">
                <div class="card-header"><a href="javascript:toggleLink('toggle')">Erweiterte Suche</a></div>
                <div class="card-block" id="toggle" style="display: none">
                    <form action="${pageContext.request.contextPath}/search" method="post">
                        <div class="form-group">
                            <label for="searchTerm">Suchbegriff: </label>
                            <input type="text" name="searchTerm" value="${term}" id="searchTerm" class="form-control"
                                   placeholder="Search"/>
                        </div>
                        <div class="form-group">
                            <label for="selectSpace">Nur im folgenden Space suchen: </label>
                            <select id="selectSpace" name="selectSpace[]" class="form-control">
                                <c:if test="${  fn:inList( selectSpace, 'All' ) }">
                                    <option value="All" selected>All</option>
                                </c:if>
                                <c:if test="${ not fn:inList( selectSpace, 'All' ) }">
                                    <option value="All">All</option>
                                </c:if>

                                <c:forEach var="u" items="${spaceRes}">
                                    <c:if test="${  fn:inList( selectSpace, u.guid ) }">
                                        <option value="${u.guid}" selected>
                                            <c:out value="${u.name}"/>
                                        </option>
                                    </c:if>
                                    <c:if test="${ not fn:inList( selectSpace, u.guid ) }">
                                        <option value="${u.guid}">
                                            <c:out value="${u.name}"/>
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <a>Nur folgende Ergebnisse anzeigen:</a>
                        <div class="form-group">
                            <label for="user"> User </label>
                            <input type="Checkbox" name="typeList[]" id="user"
                                   value="UsersChecked" ${types.contains('UsersChecked') ? 'checked' : ''} />
                            &emsp;
                            <label for="spaces"> Spaces </label>
                            <input type="Checkbox" name="typeList[]" id="spaces"
                                   value="SpacesChecked" ${types.contains('SpacesChecked') ? 'checked' : ''} />
                            &emsp;
                            <label for="post"> Posts </label>
                            <input type="Checkbox" name="typeList[]" id="post"
                                   value="PostsChecked" ${types.contains('PostsChecked') ? 'checked' : ''} />
                        </div>
                        <button class="btn btn-outline-success" type="submit"><i class="fa fa-search"></i></button>
                    </form>
                </div>
                <script type="text/javascript">
                    function toggleLink(control) {
                        var element = document.getElementById(control);
                        if (element.style.display == "none") {
                            element.style.display = "block";
                        } else {
                            element.style.display = "none";
                        }
                    }
                </script>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        var noResults = 0;
    </script>
    <div class="row">
        <div class="col">
            <c:forEach var="u" items="${searchRes}">
                <c:if test="${u.type == 'User' && types.contains('UsersChecked') }">
                    <div class="card">
                        <div class="d-flex flex-row p-2">
                            <img class="d-inline-flex p-2" style="width: 120px; height: 120px" alt="Profilbild"
                                 src="http://${pageContext.request.serverName}:8082/${u.avatarUrl}">
                            <div class="d-flex flex-column p-2">
                                <h4 class="card-title">${u.message} (${u.type}) </h4>
                                <p class="card-text">
                                    <c:if test="${not empty u.attributes}">
                                        ${u.attributes}
                                    </c:if>
                                    <c:if test="${empty u.attributes}">
                                        Keine User-Tags vorhanden
                                    </c:if>
                                    <br>
                                    <script type="text/javascript">
                                        noResults = 1;
                                    </script>
                                </p>
                                <c:if test="${not empty u.url}">
                                    <a class="card-link" target="_blank" href="${u.url}">Zum
                                        Profil</a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${u.type == 'Space' && types.contains('SpacesChecked')}">
                    <div class="card">
                        <div class="d-flex flex-row p-2">
                            <img class="d-inline-flex p-2" style="width: 120px; height: 120px" alt="Spacebild"
                                 src="http://${pageContext.request.serverName}:8082/${u.avatarUrl}">
                            <div class="d-flex flex-column p-2">
                                <h4 class="card-title">${u.message} (${u.type})</h4>
                                <p class="card-text">
                                    <c:if test="${not empty u.attributes}">
                                        ${u.attributes}<br>
                                        <script type="text/javascript">
                                            noResults = 1;
                                        </script>
                                    </c:if>
                                </p>
                                <c:if test="${not empty u.url}">
                                    <a class="card-link" target="_blank" href="${u.url}">Zum Space</a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${u.type == 'Post' && types.contains('PostsChecked')}">
                    <div class="card card-block">
                        <h4 class="card-title">${u.message} (${u.type})</h4>
                        <p class="card-text">
                            <c:if test="${not empty u.attributes}">
                                ${u.attributes}<br>
                                <script type="text/javascript">
                                    noResults = 1;
                                </script>
                            </c:if>
                        </p>
                        <c:if test="${not empty u.url}">
                            <a class="card-link" target="_blank" href="${u.url}">Zum Benutzer-Profil</a>
                        </c:if>
                    </div>
                </c:if>
            </c:forEach>
            <script type="text/javascript">
                if (noResults == 0 ||${empty searchRes}) {
                    document.write("<br><h3> Keine Suchergebnisse gefunden!</h3>");
                }
            </script>

        </div>
    </div>
</div>
</body>
</html>
