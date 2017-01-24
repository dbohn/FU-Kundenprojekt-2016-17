<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/custom-functions.tld" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demonstrator &mdash; Rollen</title>
    <meta charset="utf-8">
    <%@ include file="../partials/head.jsp" %>
</head>
<body>
<%@ include file="../partials/navigation.jsp" %>
<div class="main-header">
    <div class="jumbotron">
        <h1 class="display-4">Benutzer</h1>
        <p class="lead">Rolle bearbeiten.</p>
    </div>
</div>
<div class="container">
    <c:if test="${not empty status}">
        <div class="row">
            <div class="col">
                <div class="alert alert-success">
                        ${status}
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="row">
            <div class="col">
                <div class="alert alert-danger">
                        ${error}
                </div>
            </div>
        </div>
    </c:if>
    <div class="row">
        <div class="col">
            <hr>
            <div class="card">
                <div class="card-header">
                    Rolle bearbeiten
                </div>
                <div class="card-block">
                    <form action="${pageContext.request.contextPath}/role/edit" method="post">
                        <input type="hidden" name="role_id" value="${role.id}" />
                        <div class="form-group">
                            <label for="name">Name: </label>
                            <input type="text" name="name" id="name" class="form-control" value="${role.name}" />
                        </div>
                        <div class="form-group">
                            <label for="users">Mitglieder: </label>
                            <select class="form-control" name="users[]" id="users" multiple>
                                <c:forEach var="u" items="${userList}">
                                    <c:if test="${  fn:contains( role.users, u ) }">
                                        <option value="${u.id}" selected="selected">${u.username}</option>
                                    </c:if>
                                    <c:if test="${ not fn:contains( role.users, u ) }">
                                        <option value="${u.id}">${u.username}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Rolle speichern</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
