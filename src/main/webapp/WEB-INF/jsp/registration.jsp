<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration</title>
    <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway:400,700">
    <link rel="stylesheet" href="<c:url value="/fonts/font-awesome.min.css" /> " />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Cookie">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.8.2/css/lightbox.min.css">
    <link rel="stylesheet" href="<c:url value="/css/Pretty-Footer.css" />">
    <link rel="stylesheet" href="<c:url value="/css/Registration-Form-with-Photo.css" />">
    <link rel="stylesheet" href="<c:url value="/css/styles.css" />">
</head>

<body>
<jsp:include page="template/navigation.jsp" />
<div class="container site-section" id="welcome" style="padding-top:50px;">
    <h1>Welcome to Marlin</h1>
    <p>Marlin was founded in Paris in 1993 with the mission of giving great cake to the world. Our dedication and tasty cakes have won us numerous awards. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean id purus ipsum. </p>
</div>
<div class="register-photo">
    <div class="form-container">
        <div class="image-holder"></div>
        <form name="registerForm" method="POST" action="controller">
            <input type="hidden" name="command" value="register" />
            <h2 class="text-center"><strong>Create</strong> an account.</h2>
            <div class="form-group"><input class="form-control" type="email" name="email" placeholder="Email" required></div>
            <div class="form-group"><input class="form-control" type="text" name="name" value="" placeholder="Name" maxlength="16" required/><br/> </div>
            <div class="form-group"><input class="form-control" type="password" name="password" placeholder="Password" required></div>
            <div class="form-group">
                <input type="radio" name="role" value="client" checked="checked"/> Client
                <input type="radio" name="role" value ="courier"/> Courier
            </div>
            <div class="form-group"><button class="btn btn-primary btn-block" type="submit">Sign Up</button></div>
            <a href="#" class="already">You already have an account? Login here.</a>
        </form>
    </div>
</div>
<jsp:include page="template/footer.jsp" />
<script src="<c:url value="/js/jquery.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" />"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.8.2/js/lightbox-plus-jquery.min.js"></script>
</body>

</html>
