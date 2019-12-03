<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta http-equiv="x-ua-compatible" content="ie=edge">
  <title>Profile</title>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
  <jsp:include page="template/links.jsp" />
  <jsp:include page="template/scripts.jsp"/>
</head>
<c:set var="courier" value="COURIER" scope="page" />
<body>
  <jsp:include page="template/header.jsp"/>
  <!--Main layout-->
  <main class="mt-5 pb-5">
      <div class="container-fluid d-flex align-items-center justify-content-center">
        <div class="row d-flex justify-content-center">

          <!--Grid column-->
          <div class="col-md-7 mb-4">

            <div class="view overlay z-depth-1-half">
              <img src="<c:url value="/images?role_id=${sessionScope.id}&role=${sessionScope.role}" />" class="card-img-top" alt="avatar">
              <div class="mask rgba-white-light"></div>
            </div>

          </div>
          <!--Grid column-->

          <!--Grid column-->
          <div class="col-md-5 mb-4">

            <h2><span>${requestScope.actor.name}</span> <span>${requestScope.actor.surname}</span></h2>
            <hr>
            <p>Balance: ${requestScope.actor.balance}</p>
            <a href="<c:url value="/jsp/editProfile.jsp"/> " class="btn btn-indigo">Update profile</a>

          </div>
          <!--Grid column-->
        </div>
      </div>
  </main>
  <!--Main layout-->
<jsp:include page="template/footer.jsp" />
</body>