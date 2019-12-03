<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--Main Navigation-->
<header>

  <!--Navbar-->
  <nav class="navbar navbar-expand-lg navbar-dark indigo">

    <!-- Additional container -->
    <div class="container">

      <!-- Navbar brand -->
      <a class="navbar-brand" href="#">Marlin</a>

      <!-- Collapse button -->
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#basicExampleNav"
              aria-controls="basicExampleNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <!-- Collapsible content -->
      <div class="collapse navbar-collapse" id="basicExampleNav">

        <!-- Links -->
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href="<c:url value="/jsp/profile.jsp" />">Profile</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value="/jsp/editProfile.jsp" />">Edit Profile</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value="/jsp/offers.jsp" />">Offers</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value="/jsp/deliveries.jsp" />">Deliveries</a>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="<c:url value="/controller?command=logout" />">Logout</a>
          </li>
        </ul>
        <!-- Links -->

      </div>
      <!-- Collapsible content -->

    </div>
    <!-- Additional container -->

  </nav>
  <!--/.Navbar-->

  </header>
  <!--Main Navigation-->