
<nav class="navbar navbar-light navbar-fixed-top bg-faded">
    <a class="navbar-brand" href="#">Demonstrator</a>
    <ul class="nav navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}">Home</a>
        </li>
        <li class="nav-item${ (pageContext.request.servletPath.indexOf('users') > -1) ? " active" : "" }">
            <a class="nav-link" href="${pageContext.request.contextPath}/users">Users <span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item${ (pageContext.request.servletPath.indexOf('conversations') > -1) ? " active" : "" }">
            <a href="${pageContext.request.contextPath}/conversations" class="nav-link">Nachrichten</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="http://humhub.local:8080">Humhub</a>
        </li>
        <li class="nav-item">
        <div class="form-group">
            <form role="search" style="width: 15em; margin: 0.3em 2em;">
                <div class="input-group">
                <input type="text" class="form-control" placeholder="Search">
                <button type="submit" class="btn btn-default"><img src="search.png" height="15"></button>
                </div>
            </form>
        </div>
        </li>
    </ul>
    <ul class="nav navbar-nav float-xs-right">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="http://example.com" id="supportedContentDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${user.fullName}</a>
            <div class="dropdown-menu" aria-labelledby="supportedContentDropdown">
                <a class="dropdown-item" href="logout"
                   onclick="event.preventDefault(); document.getElementById('logout-form').submit();">
                    Logout
                </a>

                <form id="logout-form" action="logout" method="POST" style="display: none;">
                </form>
            </div>
        </li>
    </ul>
</nav>