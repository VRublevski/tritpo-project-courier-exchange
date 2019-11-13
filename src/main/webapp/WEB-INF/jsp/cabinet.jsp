<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="$sessionScope.language" />
<fmt:setBundle basename="cabinet" var="rb" />
<html>
<head>
    <title><fmt:message key="title" bundle="${rb}"/></title>
</head>
<body>
<h2><fmt:message key="header" bundle="${rb}"/></h2>
<strong><fmt:message key="form.name" bundle="${rb}"/></strong>
<c:out value="${client.name}" /> <br>
<strong><fmt:message key="form.surname" bundle="${rb}"/></strong>
<c:out value="${client.surname}" /> <br>
<form method="post" action="controller">
    <input type="hidden" name="command" value="save_personal_data">
    <b><fmt:message key="form.name" bundle="${rb}"/> </b><input type="text" name="name" size="10"> <br>
    <b><fmt:message key="form.surname" bundle="${rb}"/></b><input type="text" name="surname" size="10"> <br>
    <input type="submit" value="Save">
</form>
</body>
</html>
