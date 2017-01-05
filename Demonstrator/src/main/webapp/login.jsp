<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demonstrator</title>
    <meta charset="utf-8">
    <%@ include file="partials/head.jsp" %>
</head>
<body class="login">
<div class="container">
    <div class="row flex-items-xs-middle flex-items-xs-center">
        <div class="col-xs-7 flex-xs-middle">
            <h3 class="display-4">Login</h3>
        </div>
        <div class="col-xs-7 flex-xs-middle login-box">
            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                        Die angegebenen Nutzerdaten wurden nicht gefunden.
                </div>
            </c:if>
            <form action="${pageContext.request.contextPath}/login" method="post">

                <div class="form-group">
                    <input type="text" name="email" value="${param.email}" autofocus id="email" placeholder="E-Mail" class="form-control">
                </div>
                <div class="form-group">
                    <input type="password" name="password" id="password" placeholder="Password" class="form-control">
                </div>
                <button type="submit" class="btn btn-primary btn-block">Login</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
