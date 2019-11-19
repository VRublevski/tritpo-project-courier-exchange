<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="$sessionScope.language" />
<fmt:setBundle basename="couriers" var="rb" />
<html>
<head>
    <title><fmt:message key="title" bundle="${rb}"/></title>
    <link rel="stylesheet" href="<c:url value="/css/couriers.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/styles.css"/>">
</head>
<body>
<div id="wrapper">
        <header>
            <a href=""><img src="<c:url value="/images/logo.png"/>" alt="Logo"></a>
        </header>
        <nav>
            <ul class="top-menu">
                <li><a href="<c:url value="/home"/>">HOME</a></li>
                <li><a href="<c:url value="/cabinet"/>">CABINET</a></li>
                <li><a href="<c:url value="/jsp/couriers.jsp"/>">COURIERS</a></li>
                <li><a href="<c:url value="/controller?command=set_locale&lang=ru"/>">ru</a></li>
            </ul>
        </nav>
        <div id="heading">
            <h1>COURIERS</h1>
        </div>
        <table class="table" border="0">
            <c:forEach var="elem" items="${lst}">
            <tr>
                <td><c:out value="${elem.name}" /> </td>
                <td><c:out value="${elem.transport}" /> </td>
                <td>
                    <form method="POST" action="<c:url value="/controller" />">
                        <input type="hidden" name="command" value="request_delivery" />
                        <input type="hidden" name="courier" value="${elem.id}" />
                        <button id="login-button" name="submit" type="submit">
                            <fmt:message key="button.submit" bundle="${rb}"/>
                        </button>
                    </form>
                </td>
            </tr>
            </c:forEach>
        </table>
    <footer class="footer">
        <div id="footer">
            <div id="footer-logo">
                <a href=""><img src="<c:url value="/images/logo.png"/>" alt="Whitesquare logo"></a>
                <p>Copyright © 2012 Whitesquare. A <a href="http://pcklab.com">pcklab</a> creation</p>
            </div>
        </div>
    </footer>
</div>
</body>
</html>
