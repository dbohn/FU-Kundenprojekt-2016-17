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
            <div class="card card-block mb-2">
                <h4 class="card-title"> Letzte Nachricht: </h4>
                <c:if test="${hasConversation}">
                    <p class="card-text">
                    </p>
                    <div class="list-group mb-2">
                        <a href="conversations" class="list-group-item list-group-item-action ">
                            <p class="m-0">
                                <small>
                                        ${(lastConversation.messages)[0].user.displayName}
                                </small>
                            </p>
                            <div class="d-flex w-100 justify-content-between"><h5
                                    class="list-group-item-heading">${lastConversation.messages[0].content}</h5> <!---->
                            </div>
                            <div class="list-group-item-text conversation-info">
                                <p class="m-0">
                                    <small>
                                        Gesendet am: ${updatedMessage}
                                    </small>
                                </p>
                            </div>
                        </a>
                    </div>
                </c:if>
                <c:if test="${!hasConversation}">
                    <p class="card-text">
                        Keine Nachrichten vorhanden
                    </p>
                </c:if>
            </div>
            <div class="card card-block mb-2">
                <h4 class="card-title"> Meine Rollen: </h4>
                <c:if test="${not empty userRoles}">
                    <p class="card-text">
                    </p>
                    <c:forEach var="u" items="${userRoles}">
                        <div class="list-group mb-2">
                            <a href="users#roles" class="list-group-item list-group-item-action "> ${u.name} </a>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty userRoles}">
                    <p class="card-text">
                        Keine Rollen zugeteilt
                    </p>
                </c:if>
            </div>
        </div>
        <div class="col">
            <div class="card card-block mb-2">
                <h4 class="card-title"> Meine Projekte: </h4>

                <c:if test="${not empty projects}">
                    <p class="card-text">
                    </p>
                    <c:forEach var="u" items="${projects}">
                        <div class="list-group mb-2">
                            <a href="projects" class="list-group-item list-group-item-action "> ${u.name} </a>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty projects}">
                    <p class="card-text">
                        Keine Projekte vorhanden
                    </p>
                </c:if>
            </div>
        </div>
        <div class="col">
            <div class="card card-block mb-2">
                <h4 class="card-title"> Meine Freunde: </h4>

                <c:if test="${not empty friends}">
                    <p class="card-text">
                    </p>
                    <c:forEach var="u" items="${friends}">
                        <div class="list-group mb-2">
                            <a href="projects" class="list-group-item list-group-item-action "> ${u.username} </a>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty friends}">
                    <p class="card-text">
                        Keine Einträge vorhanden
                    </p>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
