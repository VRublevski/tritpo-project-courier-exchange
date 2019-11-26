<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Profile</title>
    <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway:400,700">
    <link rel="stylesheet" href="<c:url value="/fonts/font-awesome.min.css" /> " />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Cookie">
    <link rel="stylesheet" href="<c:url value="/css/Contact-Form-Clean.css" />" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.8.2/css/lightbox.min.css">
    <link rel="stylesheet" href="<c:url value="/css/Pretty-Footer.css" />">
    <link rel="stylesheet" href="<c:url value="/css/styles.css" />">
</head>
<body>
<jsp:include page="template/navigation.jsp" />
<div class="contact-clean">
    <h2 class="text-center">Edit profile</h2>
    <form  method="POST" action="<c:url value="/controller" />" enctype="multipart/form-data">
        <input type="hidden" name="command" value="update_profile_client" />
        <input class="form-control" type="text" name="name" placeholder="Name" style="padding-bottom:20px;padding-top:20px;margin-top:20px;">
        <input class="form-control" type="text" name="surname" placeholder="Surname" style="padding-bottom:20px;padding-top:20px;margin-top:20px;">
        <input class="form-control" type="text" name="transport" placeholder="Transport" style="padding-bottom:20px;padding-top:20px;margin-top:20px;">
        <input type="file" name="avatar">
        <div class="form-group"><button class="btn btn-primary" type="submit">Update</button></div>
    </form>
</div>
<jsp:include page="template/footer.jsp" />
<script src="<c:url value="/js/jquery.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" />"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.8.2/js/lightbox-plus-jquery.min.js"></script>
</body>

</html>