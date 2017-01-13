<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/custom-functions.tld" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demonstrator &mdash; Projekte</title>
    <meta charset="utf-8">
    <%@ include file="../partials/head.jsp" %>
</head>
<body>
<%@ include file="../partials/navigation.jsp" %>
<div class="main-header">
    <div class="jumbotron">
        <h1 class="display-4">Projekte</h1>
        <p class="lead">Projekte anlegen und Nutzer zuordnen.</p>
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
                    Projekt bearbeiten
                </div>
                <div class="card-block">
                    <form action="${pageContext.request.contextPath}/projects/edit" method="post">
                        <input type="hidden" name="project_id" value="${project.id}" />
                        <div class="form-group">
                            <label for="name">Name: </label>
                            <input type="text" name="name" id="name" class="form-control" value="${project.name}" />
                        </div>
                        <div class="form-group">
                            <label for="description">Beschreibung: </label>
                            <textarea type="text" rows="5" name="description" id="description" class="form-control">${project.description}</textarea>
                        </div>
                        <div class="form-group">
                            <label for="users">Mitglieder: </label>
                            <select class="form-control" name="users[]" id="users" multiple>
                                <c:forEach var="u" items="${userList}">
                                    <c:if test="${  fn:contains( project.users, u ) }">
                                        <option value="${u.id}" selected="selected">${u.username}</option>
                                    </c:if>
                                    <c:if test="${ not fn:contains( project.users, u ) }">
                                        <option value="${u.id}">${u.username}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Projekt speichern</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
