<%@ page import="de.fuberlin.kundenprojekt.friedrich.UserRepository" %>
<%@ page import="de.fuberlin.kundenprojekt.friedrich.models.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demonstrator &mdash; Users</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="bootstrap-flex.min.css">
    <link rel="stylesheet" href="style.css">
    <script src="js/jquery-3.1.1.min.js"></script>
    <script src="js/tether.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-light navbar-fixed-top bg-faded">
    <a class="navbar-brand" href="#">Navbar</a>
    <ul class="nav navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="index.jsp">Home</a>
        </li>
        <li class="nav-item active">
            <a class="nav-link" href="users.jsp">Users <span class="sr-only">(current)</span></a>
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
                <h1 class="display-3">Users</h1>
                <p class="lead">Benutzer anlegen, ändern, entfernen.</p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs">
            <table class="table">
                <thead>
                <tr>
                    <th>Username</th>
                    <th>Full Name</th>
                    <th>E-Mail</th>
                    <th>Phone</th>
                </tr>
                </thead>
                <tbody>
                <%for(User u: UserRepository.getUserList()) {%>
                    <tr>
                        <td><%=u.username%></td>
                        <td><%=u.full_name%></td>
                        <td><%=u.email%></td>
                        <td><%=u.phone%></td>
                    </tr>
                <%}%>
                </tbody>
            </table>

        </div>
    </div>
    <div class="row">
        <div class="col-xs">
            <hr>
            <div class="card">
                <div class="card-header">
                    Massenimport
                </div>
                <div class="card-block">
                    <form action="csvimport" method="post" enctype="multipart/form-data">
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
        <div class="col-xs">
            <hr>
            <div class="card">
                <div class="card-header">
                    Neuen Nutzer anlegen
                </div>
                <div class="card-block">
                    <form action="users" method="post">
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
