<nav class="navbar navbar-light navbar-fixed-top bg-faded">
    <a class="navbar-brand" href="#">Demonstrator</a>
    <ul class="nav navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}">Home</a>
        </li>
        <li class="nav-item${ (pageContext.request.servletPath.indexOf('users') > -1) ? " active" : "" }">
            <a class="nav-link" href="${pageContext.request.contextPath}/users">Users <span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="http://humhub.local:8080">Humhub</a>
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