<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-inverse navbar-fixed-top" style="background-color:rgb(48,180,255);">
    <div class="container">
        <div class="navbar-header"><a class="navbar-brand" href="#"><img src="assets/img/logo.png" id="logo"><strong>Mom &amp; Pop's</strong> Bakery</a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li role="presentation"><a href="<c:url value="/jsp/authentification.jsp>"/> "> Login</a></li>
            <li role="presentation"><a href="<c:url value="/jsp/registration.jsp>"/> ">Register</a></li>
            <li role="presentation"><a href="<c:url value="/jsp/couriers.jsp"/>">Couriers</a></li>
            <li role="presentation"><a href="<c:url value="/jsp/deliveries.jsp"/>">Deliveries</a></li>
            <li role="presentation"><a href="<c:url value="/jsp/profile.jsp"/>">Profile</a></li>
            <li role="presentation"><a href="<c:url value="/jsp/editProfile.jsp"/>">Edit profile</a></li>
            <li role="presentation"><a href="<c:url value="/controller?command=set_locale&lang=ru"/>" style="background-color:#ffffff;">ru</a></li>
        </ul>
    </div>
</nav>