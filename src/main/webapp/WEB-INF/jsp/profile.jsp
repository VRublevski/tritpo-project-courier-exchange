<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway:400,700">
    <link rel="stylesheet" href="<c:url value="/fonts/font-awesome.min.css" /> " />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Cookie">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.8.2/css/lightbox.min.css">
    <link rel="stylesheet" href="<c:url value="/css/Pretty-Footer.css" />">
    <link rel="stylesheet" href="<c:url value="/css/styles.css" />">
</head>
<body>
<jsp:include page="template/navigation.jsp" />
<c:set var="courier" value="COURIER" scope="page" />
<div class="container site-section" id="welcome" style="padding-top:50px;">
    <h1>Profile</h1>
    <div class="table-responsive" style="width:600px;margin-left:auto;margin-right:auto;">
        <table class="table">
            <tbody>
            <tr style="width:50px;">
                <td style="width:25%;">
                    <img src="<c:url value="/images?role_id=${sessionScope.id}&role=${sessionScope.role}" />" style="width:100px;height:100px;" >
                    <button class="btn btn-default" type="button" style="width:100px;padding-top:20px;">Edit profile</button></td>
                <td style="width:75%;">
                    <h1 style="margin-top:0;margin-bottom:15px;"><c:out value="${requestScope.actor.name}" /></h1>
                    <p class="text-left">${requestScope.actor.surname}</p>
                    <p class="text-left">${requestScope.actor.balance}</p>
                    <c:if test="${sessionScope.role == pageScope.courier}" >
                        <h3 style="margin-top:0;margin-bottom:15px;">Offer</h3>
                        <p class="text-left">${requestScope.offer.transport}</p>
                        <p class="text-left">${requestScope.offer.price}</p>
                    </c:if>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="template/footer.jsp" />
<script src="<c:url value="/js/jquery.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" />"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.8.2/js/lightbox-plus-jquery.min.js"></script>
</body>

</html>