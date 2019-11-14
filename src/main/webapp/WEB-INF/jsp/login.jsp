<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="login" var="rb" />
<html lang="$session.lang">
<head>
    <title><fmt:message key="title" bundle="${rb}"/></title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<c:url value="/css/styles.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/login.css"/>">
</head>
<body>
<div id="wrapper">
    <header>
        <a href=""><img src="<c:url value="/images/logo.png"/>" alt="Logo"></a> <br>
        <a href="<c:url value="/controller?command=set_locale&lang=ru&page=login"/>">ru</a>
    </header>
    <div id="heading">
        <h1>LOGIN</h1>
    </div>
    <div class="form">
        <form name="loginForm" method="POST" action="controller" class="form-signin">
            <input type="hidden" name="command" value="login" />
            <input type="email" name="email" value="" placeholder="<fmt:message key="form.email" bundle="${rb}" />" maxlength="16" required autofocus/><br/>
            <input type="password" name="password" value="" placeholder="<fmt:message key="form.password" bundle="${rb}" />" maxlength="16" required/><br/>
            <button id="login-button" name="submit" type="submit">
                <fmt:message key="button.submit" bundle="${rb}"/>
            </button>
        </form>
    </div>
</div>
<footer class="footer">
    <div id="footer">
        <div id="footer-logo">
            <a href=""><img src="<c:url value="/images/logo.png"/>" alt="Whitesquare logo"></a>
            <p>Copyright © 2012 Whitesquare. A <a href="http://pcklab.com">pcklab</a> creation</p>
        </div>
    </div>
</footer>
</body>
</html>