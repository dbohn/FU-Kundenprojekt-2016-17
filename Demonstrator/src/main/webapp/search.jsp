<%--
  Created by IntelliJ IDEA.
  User: hanna
  Date: 09.12.2016
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>search</title>
    <title>Demonstrator &mdash; Search</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-flex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/tether.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<%@ include file="partials/navigation.jsp" %>
    <div class="container">
     <div class="row">
         <div class="col-xs">
                <hr>
               <div class="card">
                  <div class="card-header">
                          Suche
                  </div>
                 <div class="card-block">
                     <form action="${pageContext.request.contextPath}/search" method="post" enctype="multipart/form-data">
                            <div class="form-group">
                             <input type="text" id="searchData" name="searchData" class="form-control">
                         </div>
                            <button type="submit" class="btn btn-primary">Suche</button>
                     </form>
                    </div>
             </div>
         </div>
     </div>
    </div>
</body>
</html>
