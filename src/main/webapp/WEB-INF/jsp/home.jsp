<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>Home</title>
    <link rel="stylesheet" href="<c:url value="/css/home.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/styles.css"/>">
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Oswald:400,300" type="text/css">
</head>
<body>
    <div id="wrapper">
        <header>
            <a href="/"><img src="<c:url value="/images/logo.png"/>" alt="Logo"></a>
        </header>
        <nav>
            <ul class="top-menu">
                <li><a href="<c:url value="/home"/>">HOME</a></li>
                <li><a href="<c:url value="/cabinet"/>">CABINET</a></li>
                <li><a href="<c:url value="/couriers"/>">COURIERS</a></li>
            </ul>
        </nav>
        <div id="heading">
            <h1>HOME</h1>
        </div>
        <section>
            <blockquote>
                <p>
                    “QUISQUE IN ENIM VELIT, AT DIGNISSIM EST. NULLA UL CORPER, DOLOR AC PELLENTESQUE PLACERAT, JUSTO TELLUS GRAVIDA ERAT, VEL PORTTITOR LIBERO ERAT.”
                </p>
                <cite>John Doe, Lorem Ipsum</cite>
            </blockquote>
        </section>
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
