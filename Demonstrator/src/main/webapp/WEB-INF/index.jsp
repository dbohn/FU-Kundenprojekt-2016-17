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
            <p>Diese Seite basiert auf <a href="http://v4-alpha.getbootstrap.com">Bootstrap</a>, um eine Grundoptik zu
                bieten</p>
        </div>
        <div class="col">
            <div class="card card-block">
                <h4 class="card-title"> Letzte Nachricht: </h4>
                <p class="card-text">
                </p>
                <div class="list-group">
                    <a href="conversations" class="list-group-item list-group-item-action active">
                        <p class="m-0">
                            <small>
                                ${(lastConversation.messages)[0].user.displayName}
                            </small>
                        </p>
                        <div class="d-flex w-100 justify-content-between"><h5
                                class="list-group-item-heading">${lastConversation.messages[0].content}</h5> <!----></div>
                        <div class="list-group-item-text conversation-info">
                            <p class="m-0">
                                <small>
                                    Gesendet am: ${updatedMessage}
                                </small>
                            </p>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
