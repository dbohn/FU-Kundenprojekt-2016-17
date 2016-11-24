<%@ page import="main.Dummy" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demonstrator</title>
    <link rel="stylesheet" href="resources/bootstrap-flex.min.css">
    <link rel="stylesheet" href="resources/style.css">
</head>
<body>
<nav class="navbar navbar-light navbar-fixed-top bg-faded">
    <a class="navbar-brand" href="#">Navbar</a>
    <ul class="nav navbar-nav">
        <li class="nav-item active">
            <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">Link</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">Link</a>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="http://example.com" id="supportedContentDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Dropdown</a>
            <div class="dropdown-menu" aria-labelledby="supportedContentDropdown">
                <a class="dropdown-item" href="#">Action</a>
                <a class="dropdown-item" href="#">Another action</a>
                <a class="dropdown-item" href="#">Something else here</a>
            </div>
        </li>
    </ul>
</nav>
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
                <p><%=Dummy.doSomething() %></p>
            </div>
        </div>
    </div>
</body>
</html>
