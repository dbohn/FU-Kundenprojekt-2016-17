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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-flex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/tether.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body class="search">
<%@ include file="partials/navigation.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-xs">
            <div class="jumbotron">
                <h1 class="display-3">Suche</h1>
                <p class="lead">Suchergebnisse für ${term}</p>
                <a href="javascript:toggleLink('toggle')"> Erweiterte Suche </a>
                <br/>
                <div id="toggle" style="display: none">
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
                    }</script>
                <hr /><br/>
                <c:forEach var="u" items="${searchRes}">
                    <c:if test="${u.type == 'User' && types.contains('UsersChecked') }">
                        <c:if test="${not empty u.url}">
                            <a target="_blank" href="${u.url}">${u.message} (${u.type})</a><br>
                            <SMALL>${u.url}</SMALL>
                            <br>
                            <c:if test="${not empty u.attributes}">
                                ${u.attributes}<br>
                            </c:if>
                            <br>
                        </c:if>
                        <c:if test="${empty u.url}">
                            ${u.message} (${u.type})<br>
                            ${u.attributes}<br><br>
                        </c:if>
                    </c:if>
                    <c:if test="${u.type == 'Space' && types.contains('SpacesChecked')}">
                        <c:if test="${not empty u.url}">
                            <a target="_blank" href="${u.url}">${u.message} (${u.type})</a><br>
                            <SMALL>${u.url}</SMALL>
                            <br>
                            <c:if test="${not empty u.attributes}">
                                ${u.attributes}<br>
                            </c:if>
                            <br>
                        </c:if>
                        <c:if test="${empty u.url}">
                            ${u.message} (${u.type})<br>
                            ${u.attributes}<br><br>
                        </c:if>
                    </c:if>
                    <c:if test="${u.type == 'Post' && types.contains('PostsChecked')}">
                        <c:if test="${not empty u.url}">
                            <a target="_blank" href="${u.url}">${u.message} (${u.type})</a><br>
                            <SMALL>${u.url}</SMALL>
                            <br>
                            <c:if test="${not empty u.attributes}">
                                ${u.attributes}<br>
                            </c:if>
                            <br>
                        </c:if>
                        <c:if test="${empty u.url}">
                            ${u.message} (${u.type})<br>
                            ${u.attributes}<br><br>
                        </c:if>
                    </c:if>


                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
