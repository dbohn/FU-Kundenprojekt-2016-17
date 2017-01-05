<%@ page import="de.fuberlin.kundenprojekt.friedrich.UserRepository" %>
<%@ page import="de.fuberlin.kundenprojekt.friedrich.PasswordHasher" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demonstrator</title>
    <meta charset="utf-8">
    <%@ include file="partials/head.jsp" %>
</head>
<body>
<%@ include file="partials/navigation.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-xs">
            <div class="jumbotron">
                <h1 class="display-3">Demonstrator</h1>
                <p class="lead">Demonstration einer Integration von Humhub in ein BCS-System</p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs">
            <p>Diese Seite basiert auf <a href="http://v4-alpha.getbootstrap.com">Bootstrap</a>, um eine Grundoptik zu bieten</p>
        </div>
        <div class="col-xs">
            <p>Test 2</p>
        </div>
    </div>
</div>
</body>
</html>
