<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="chat">
<head>
    <title>Demonstrator &mdash; Nachrichten</title>
    <meta charset="utf-8">
    <%@ include file="../partials/head.jsp" %>
</head>
<body>
<%@ include file="../partials/navigation.jsp" %>
<div class="main-header">
    <div class="jumbotron">
        <h1 class="display-4">Nachrichten</h1>
    </div>
</div>
<div class="container">
    <c:if test="${not empty status}">
    <div class="row">
        <div class="col-xs">
            <div class="alert alert-success">
                    ${status}
            </div>
        </div>
    </div>
    </c:if>
<div class="container" id="app">
    <chat-app></chat-app>
</div>
<script>
    const Settings = {
        me: "${user.id}",
        thisUrl: "${pageContext.request.contextPath}",
    };
</script>
    <script src="${pageContext.request.contextPath}/app.js"></script>
</body>
</html>
