<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demonstrator</title>
    <meta charset="utf-8">
    <%@ include file="../partials/head.jsp" %>
</head>
<body>
<%@ include file="../partials/navigation.jsp" %>
<div class="main-header">
    <div class="jumbotron">
        <h1 class="display-4">Demonstrator</h1>
        <p class="lead">Demonstration einer Integration von Humhub in ein BCS-System</p>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col">
            <ul>
            <c:forEach var="project" items="${projects}">
                <li>${project.name}</li>
            </c:forEach>
            </ul>
            <p>Diese Seite basiert auf <a href="http://v4-alpha.getbootstrap.com">Bootstrap</a>, um eine Grundoptik zu bieten</p>
        </div>
        <div class="col">
            <p>Test 2</p>
        </div>
    </div>
</div>
</body>
</html>
