<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta http-equiv="x-ua-compatible" content="ie=edge">
  <title>Edit profile</title>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
  <jsp:include page="template/links.jsp" />
  <jsp:include page="template/scripts.jsp" />
</head>
<c:set var="courier" value="COURIER" scope="page" />
<c:choose >
    <c:when test="${sessionScope.role == pageScope.courier}">
        <c:set var="command" value="update_profile_courier" scope="page" />
    </c:when>
    <c:otherwise>
        <c:set var="command" value="update_profile_client" scope="page" />
    </c:otherwise>
</c:choose>
<body>
    <jsp:include page="template/header.jsp" />
    <!--Main layout-->
    <main class="mt-5 pb-5">

        <!--Main container-->
        <div class="container-fluid d-flex align-items-center justify-content-center">
            <div class="row d-flex justify-content-center"> 
            
            <!-- Default form register -->
            <form class="text-center border border-light p-5" method="POST" action="controller" enctype="multipart/form-data">
                <input type="hidden" name="command" value="${pageScope.command}" />
                <p class="h4 mb-4">Update profile</p>
    
                <div class="form-row mb-4">
                    <div class="col">
                        <!-- First name -->
                        <input type="text" class="form-control" name="name" placeholder="First name">
                    </div>
                    <div class="col">
                        <!-- Last name -->
                        <input type="text" class="form-control" name="surname" placeholder="Last name">
                    </div>
                </div>

                <input type="number" class="form-control" name="balance" placeholder="Balance">

                <!-- Offer -->
                <c:if test="${sessionScope.role == pageScope.courier}" >
                    <h3 class="pt-2">Offer</h3>
                    <input type="text"  class="form-control mb-4"   name="transport" placeholder="Transport" required>
                    <input type="number"  class="form-control mb-4" name="price" placeholder="Price" required>
                </c:if>

                <div class="input-group pt-4">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
                    </div>
                    <div class="custom-file">
                        <input type="file" class="custom-file-input" id="inputGroupFile01" name="avatar" aria-describedby="inputGroupFileAddon01">
                        <label class="custom-file-label" for="inputGroupFile01">Profile photo</label>
                    </div>
                </div>

                <!-- Sign up button -->
                <button class="btn btn-info my-4 btn-block" type="submit">Update profile</button>
    
            </form>
            <!-- Default form register -->
            </div>
            </div>
        <!--Main container-->
    
    </main>
    <!--Main layout-->
    <jsp:include page="template/footer.jsp" />
</body>
</html>
