
<nav class="navbar navbar-light navbar-toggleable-md fixed-top bg-faded">
    <a class="navbar-brand" href="#">Demonstrator</a>
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
        </li>
        <li class="nav-item${ (pageContext.request.servletPath.indexOf('users') > -1) ? " active" : "" }">
            <a class="nav-link" href="${pageContext.request.contextPath}/users">Users <span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item${ (pageContext.request.servletPath.indexOf('projects') > -1) ? " active" : "" }">
            <a href="${pageContext.request.contextPath}/projects" class="nav-link">Projekte</a>
        </li>
        <li class="nav-item${ (pageContext.request.servletPath.indexOf('messages') > -1) ? " active" : "" }">
            <a href="${pageContext.request.contextPath}/conversations" class="nav-link">Nachrichten</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" target="_blank" href="http://humhub.local:8082">Humhub</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="logout" onclick="event.preventDefault(); document.getElementById('logout-form').submit();">Abmelden [${user.fullName}]</a>
            <form id="logout-form" action="logout" method="POST" style="display: none;">
            </form>
        </li>
    </ul>
    <form class="form-inline float-xs-right" style="margin-bottom:0;margin-left:5px;" action="${pageContext.request.contextPath}/search" method="post">
        <input type="hidden" name="typeList[]" value="UsersChecked">
        <input type="hidden" name="typeList[]" value="SpacesChecked">
        <input type="hidden" name="typeList[]" value="PostsChecked">
        <input type="hidden" name="selectSpace[]" value="All">
        <input type="text" name="searchTerm" id="searchTerm" class="form-control" placeholder="Search" />
        <button class="btn btn-outline-success" type="submit"><i class="fa fa-search"></i></button>
    </form>
</nav>