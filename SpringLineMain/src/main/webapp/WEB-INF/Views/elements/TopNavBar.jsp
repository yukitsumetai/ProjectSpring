<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 14.03.2019
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>

<!-- navigation Bar -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="${contextPath}/resource/css/dashboard.css" rel="stylesheet">
    <!-- Bootstrap core CSS -->
    <link href="${contextPath}/resource/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
</head>


<nav class="navbar navbar-inverse sticky-top flex-md-nowrap p-0">
    <a class=" brand navbar-brand text-light">
        <img src="${contextPath}/resource/images/conHp.png" width="30" height="30" class="d-inline-block align-top"
             alt="">
        Spring Line</a>

    <div class="navbar-nav mr-auto">
        <li class="nav-item"><a class="text-secondary" href="${contextPath}/logout">Logout</a></li>
    </div>
    <c:if test="${table=='add'}">
        <ul class=" my-2 my-lg-0">
            <li id="cd-cart-trigger" class="nav-item"/><a class="cd-img-replace" href="#0"></a></li>
        </ul>
    </c:if>
    <c:if test="${table=='add'}">
        <%@ include file="shoppingCart.jsp" %>
    </c:if>

</nav>

