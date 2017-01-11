<%--
  Created by IntelliJ IDEA.
  User: hanna
  Date: 09.12.2016
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demonstrator &mdash; Search</title>
    <meta charset="utf-8">
    <%@ include file="partials/head.jsp" %>
</head>
<body class="search">
<%@ include file="partials/navigation.jsp" %>
<div class="main-header">
    <div class="jumbotron">
        <h1 class="display-4">Suche</h1>
        <p class="lead">Suchergebnisse für ${term}</p>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-xs">
            <div class="card">
                <div class="card-header"><a href="javascript:toggleLink('toggle')">Erweiterte Suche</a></div>
                <!--<a href="javascript:toggleLink('toggle')"> Erweiterte Suche </a>-->
                <div class="card-block" id="toggle" style="display: none">
                    <div class="form-group">
                        <label for="space">Nur im folgendem Space suchen: </label>
                        <input type="text" name="Space:" id="space"/>
                    </div>
                    <a>Nur folgende Ergebnisse anzeigen:</a>
                    <div class="form-group">
                        <label for="user"> User </label>
                        <input type="Checkbox" name="typeList[]" id="user" value="UsersChecked" ${types.contains('UsersChecked') ? 'checked' : ''} />
                        &emsp;
                        <label for="spaces"> Spaces </label>
                        <input type="Checkbox" name="typeList[]" id="spaces" value="SpacesChecked" ${types.contains('SpacesChecked') ? 'checked' : ''} />
                        &emsp;
                        <label for="post"> Posts </label>
                        <input type="Checkbox" name="typeList[]" id="post" value="PostsChecked" ${types.contains('PostsChecked') ? 'checked' : ''} />
                    </div>
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
    <div class="row">
        <div class="col-xs">
            <c:forEach var="u" items="${searchRes}">
                <c:if test="${u.type == 'User' && types.contains('UsersChecked') }">
                    <div class="card card-block">
                        <h4 class="card-title">${u.message} (${u.type})</h4>
                        <p class="card-text">
                            <c:if test="${not empty u.attributes}">
                                ${u.attributes}<br>
                            </c:if>
                        </p>
                        <c:if test="${not empty u.url}">
                            <a class="card-link" target="_blank" href="${u.url}">Zum Profil</a>
                        </c:if>
                    </div>
                </c:if>
                <c:if test="${u.type == 'Space' && types.contains('SpacesChecked')}">
                    <div class="card card-block">
                        <h4 class="card-title">${u.message} (${u.type})</h4>
                        <p class="card-text">
                            <c:if test="${not empty u.attributes}">
                                ${u.attributes}<br>
                            </c:if>
                        </p>
                        <c:if test="${not empty u.url}">
                            <a class="card-link" target="_blank" href="${u.url}">Zum Space</a>
                        </c:if>
                    </div>
                </c:if>
                <c:if test="${u.type == 'Post' && types.contains('PostsChecked')}">
                    <div class="card card-block">
                        <h4 class="card-title">${u.message} (${u.type})</h4>
                        <p class="card-text">
                            <c:if test="${not empty u.attributes}">
                                ${u.attributes}<br>
                            </c:if>
                        </p>
                        <c:if test="${not empty u.url}">
                            <a class="card-link" target="_blank" href="${u.url}">Zum Benutzer-Profil</a>
                        </c:if>
                    </div>
                </c:if>
            </c:forEach>
            <c:if test="${empty searchRes}">
                <h3 style="text-align: center">Keine Suchergebnisse gefunden!</h3>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
