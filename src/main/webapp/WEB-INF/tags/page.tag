<%@ tag description="Generic Page" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="pageTitle" required="true" %>
<%@ attribute name="navBar" required="true" fragment="true" %>
<%@ attribute name="jsIncludes" fragment="true" %>
<%@ attribute name="cssFile" %>

<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" type="text/css" href="/css/styles.css">

    <!-- Bootstrap core CSS -->
    <link href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" rel="stylesheet">
    <!-- Material Design Bootstrap -->
    <link href="/webjars/mdbootstrap-bootstrap-material-design/4.5.3/css/mdb.css" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.min.css">

    <c:if test="${not empty cssFile}">
        <link rel="stylesheet" type="text/css" href="/css/${cssFile}">
    </c:if>

    <script type="text/javascript" charset="utf8" src="/webjars/jquery/3.2.1/jquery.min.js"></script>

    <!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>

    <script type="text/javascript" charset="utf8" src="/webjars/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>

    <jsp:invoke fragment="jsIncludes" />
</head>
<body>
    <jsp:invoke fragment="navBar" />
    <main id="mainContent">
        <div class="container">
            <jsp:doBody />
        </div>
    </main>
</body>
</html>