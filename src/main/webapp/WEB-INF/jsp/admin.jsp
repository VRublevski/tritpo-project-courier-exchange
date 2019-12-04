<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Admin</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
    <jsp:include page="template/links.jsp" />
    <jsp:include page="template/scripts.jsp"/>
</head>
<c:set var="role" value="COURIER" scope="page"/>
<body>
<header>

    <!--Navbar-->
    <nav class="navbar navbar-expand-lg navbar-dark indigo">

        <!-- Additional container -->
        <div class="container">

            <!-- Navbar brand -->
            <a class="navbar-brand" href="#">Marlin</a>

            <!-- Collapse button -->
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#basicExampleNav"
                    aria-controls="basicExampleNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- Collapsible content -->
            <div class="collapse navbar-collapse" id="basicExampleNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=logout" />">Logout</a>
                    </li>
                </ul>
                <!-- Links -->

            </div>
            <!-- Collapsible content -->

        </div>
        <!-- Additional container -->

    </nav>
    <!--/.Navbar-->

</header>
<!--Main Navigation-->
<main class="mt-5 pb-5" >
    <div class="container">
        <!--Grid row-->
        <div class="row">
            <!--Grid column-->
            <c:forEach var="elem" items="${requestScope.user_list}" varStatus="status">
                <div class="col-lg-4 col-md-6 mb-4">
                    <!--Card-->
                    <div class="card">
                        <!--Card image-->
                        <div class="view overlay">
                            <div class="embed-responsive embed-responsive-4by3">
                                <img src="<c:url value="/images?role_id=${requestScope.actor_map[elem.id].id}&role=${elem.role}" />" class="card-img-top embed-responsive-item"
                                    alt="courier">
                            </div>
                            <a href="#">
                                <div class="mask rgba-white-slight"></div>
                            </a>
                        </div>
                        <!--Card content-->
                        <div class="card-body">
                            <!--Title-->
                            <h4 class="card-title"><span>${requestScope.actor_map[elem.id].name}</span> <span>${requestScope.actor_map[elem.id].surname}</span></h4>
                            <!--Text-->
                            <p class="card-text"><span>Email: ${elem.email}</span></p>
                            <p class="card-text"><span>Role: ${elem.role}</span></p>
                            <p class="card-text"><span>Balance: ${requestScope.actor_map[elem.id].balance}</span></p>
                            <a href="<c:url value="/controller?command=delete_user&id=${elem.id}&role=${elem.role}"/>" class="btn btn-indigo">Delete</a>
                        </div>
                    </div>
                    <!--/.Card-->
                </div>
                <!--Grid column-->
            </c:forEach>
        </div>
        <!--Grid row-->
    </div>
    <!--Main container-->
</main>
<!--Main layout-->
<jsp:include page="template/footer.jsp" />
</body>
</html>
