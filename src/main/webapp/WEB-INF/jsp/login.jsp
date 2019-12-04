<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Login</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
    <jsp:include page="template/links.jsp" />
    <jsp:include page="template/scripts.jsp"/>
</head>
<body>
    <!--Main layout-->
    <main class="mt-5">

        <!--Main container-->
        <div class="container-fluid d-flex align-items-center justify-content-center">
          <div class="row d-flex justify-content-center">

            <!-- Default form login -->
            <form class="text-center border border-light p-5" method="POST" action="<c:url value="/controller" />">
                <input type="hidden" name="command" value="login" />
                <p class="h4 mb-4">Sign in</p>

                  <!-- Email -->
                <input type="email" class="form-control mb-4" name="email" placeholder="E-mail">

                  <!-- Password -->
                <input type="password" class="form-control mb-4" name="password" placeholder="Password">

                  <!-- Sign in button -->
                <button class="btn btn-info btn-block my-4" type="submit">Sign in</button>

                <c:if test="${not empty requestScope.error_string}">
                    <div class="alert alert-danger alert-dismissible fade show">
                            ${requestScope.error_string}
                    </div>
                </c:if>
                  <!-- Register -->
                <p>Not a member?
                    <a href="<c:url value="/jsp/register.jsp" /> ">Register</a>
                </p>

            </form>
            <!-- Default form login -->
          </div>


        </div>
        <!--Main container-->

      </main>
      <!--Main layout-->
</body>
</html>