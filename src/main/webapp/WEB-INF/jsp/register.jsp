<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="$sessionScope.locale"/>
<fmt:setBundle basename="register" var="rb" />
<html lang="$sessionScope.locale">
<head>
    <title><fmt:message key="title" bundle="${rb}"/></title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<c:url value="/css/styles.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/register.css"/>">
</head>
<body>
<div id="wrapper">
    <header>
        <a href="/"><img src="<c:url value="/images/logo.png"/>" alt="Logo"></a>
    </header>
    <nav>
        <ul class="top-menu">
            <li><a href="<c:url value="/login"/>">LOGIN</a></li>
        </ul>
    </nav>
    <div id="heading">
        <h1>REGISTER</h1>
    </div>
    <div class="form">
        <form name="registerForm" method="POST" action="controller" class="form-signin">
            <input type="hidden" name="command" value="register" />
            <input type="text" name="email" value="" placeholder="<fmt:message key="form.email" bundle="${rb}" />" maxlength="16" required autofocus/><br/>
            <input type="text" name="name" value="" placeholder="<fmt:message key="form.name" bundle="${rb}" />" maxlength="16" required/><br/>
            <input type="password" name="password" value="" placeholder="<fmt:message key="form.password-repeat" bundle="${rb}" />" maxlength="16" required/><br/>
            <input type="password" name="password-repeat" value="" placeholder="<fmt:message key="form.password" bundle="${rb}" />" maxlength="16" required/><br/>
            <button id="register-button" name="submit" type="submit">
                <fmt:message key="button.register" bundle="${rb}"/>
            </button>
        </form>
    </div>
</div>
<footer class="footer">
    <div id="footer">
        <div id="footer-logo">
            <a href="/"><img src="<c:url value="/images/logo.png"/>" alt="Whitesquare logo"></a>
            <p>Copyright © 2012 Whitesquare. A <a href="http://pcklab.com">pcklab</a> creation</p>
        </div>
    </div>
</footer>
</body>
</html>