<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<div class="container">
    <div class="row flex-items-xs-middle flex-items-xs-center">
        <div class="col-xs-7 flex-xs-middle">
            <h3>Login</h3>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                        Die angegebenen Nutzerdaten wurden nicht gefunden.
                </div>
            </c:if>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <input type="text" name="email" id="email" placeholder="E-Mail" class="form-control">
                <input type="password" name="password" id="password" placeholder="Password" class="form-control">
                <button type="submit" class="btn btn-outline-primary btn-block">Login</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
