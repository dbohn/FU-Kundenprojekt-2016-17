<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Beschreibung</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="project" items="${projectsList}">
                    <tr>
                        <td>${project.name}</td>
                        <td>${project.description}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/projects/delete" method="post">
                                <a href="${pageContext.request.contextPath}/projects/edit?project_id=${project.id}"
                                   class="btn btn-secondary btn-sm"><i class="fa fa-pencil"></i></a>
                                <input type="hidden" name="project_id" value="${project.id}">
                                <!--<button type="submit" class="btn btn-danger btn-sm" title="Projekt löschen">
                                    <i class="fa fa-trash-o"></i>
                                </button>-->
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
    <div class="row">
        <div class="col">
            <hr>
            <div class="card">
                <div class="card-header">
                    Neues Projekt erstellen
                </div>
                <div class="card-block">
                    <form action="${pageContext.request.contextPath}/projects" method="post">
                        <div class="form-group">
                            <label for="name">Name: </label>
                            <input type="text" name="name" id="name" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label for="description">Beschreibung: </label>
                            <textarea type="text" rows="5" name="description" id="description" class="form-control"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="users">Mitglieder: </label>
                            <select class="form-control" name="users[]" id="users" multiple>
                                <c:forEach var="u" items="${userList}">
                                    <option value="${u.id}" ${(u.id == user.id) ? " selected=\"selected\"" : ""}>${u.username}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Neues Projekt anlegen</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
