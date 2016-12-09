<%@ page import="de.fuberlin.kundenprojekt.friedrich.UserRepository" %>
<%@ page import="de.fuberlin.kundenprojekt.friedrich.PasswordHasher" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demonstrator</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-flex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/tether.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
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
    <div class="row">
        <div class="col-xs">
            <hr>
            <div class="card">
                <div class="card-header">
                    Suche
                </div>
                <div class="card-block">
                    <form action="${pageContext.request.contextPath}/search" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <input type="text" id="searchData" name="searchData" class="form-control">
                        </div>
                        <button type="submit" class="btn btn-primary">Suche</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
