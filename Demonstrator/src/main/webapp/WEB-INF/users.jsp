<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demonstrator &mdash; Users</title>
    <meta charset="utf-8">
    <%@ include file="../partials/head.jsp" %>
</head>
<body>
<%@ include file="../partials/navigation.jsp" %>
<div class="main-header">
    <div class="jumbotron">
        <h1 class="display-4">Users</h1>
        <p class="lead">Benutzer anlegen, ändern, entfernen.</p>
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
                    <th>Benutzername</th>
                    <th>Vollständiger Name</th>
                    <th>E-Mail</th>
                    <th>Telefon</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="u" items="${userList}">
                    <tr${(u.id == user.id) ? " class=\"table-info\"" : ""}>
                        <td>${u.username}
                            <c:if test="${u.id == user.id}">
                                <span class="badge badge-pill badge-info">Ich</span>
                            </c:if>
                        </td>
                        <td>${u.fullName}</td>
                        <td>${u.email}</td>
                        <td>${u.phone}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/user/delete" method="post">
                                <input type="hidden" name="user_id" value="${u.id}">
                                <button type="submit" class="btn btn-danger" title="Benutzer löschen"><i class="fa fa-trash-o"></i></button>
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
                    Massenimport
                </div>
                <div class="card-block">
                    <form action="${pageContext.request.contextPath}/csvimport" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="csvfile">CSV-Datei:</label>
                            <input type="file" id="csvfile" name="csvfile" class="form-control-file">
                        </div>
                        <button type="submit" class="btn btn-primary">Import</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <hr>
            <div class="card">
                <div class="card-header">
                    Neuen Nutzer anlegen
                </div>
                <div class="card-block">
                    <form action="${pageContext.request.contextPath}/users" method="post">
                        <div class="form-group">
                            <label for="username">Username: </label>
                            <input type="text" name="username" id="username" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label for="email">E-Mail: </label>
                            <input type="text" name="email" id="email" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label for="first_name">Vorname: </label>
                            <input type="text" name="first_name" id="first_name" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label for="last_name">Nachname: </label>
                            <input type="text" name="last_name" id="last_name" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label for="password">Passwort: </label>
                            <input type="password" name="password" id="password" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label for="phone">Telefon: </label>
                            <input type="text" name="phone" id="phone" class="form-control" />
                        </div>
                        <button class="btn btn-primary">Neuen Nutzer anlegen</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
