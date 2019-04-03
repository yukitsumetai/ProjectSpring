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
<c:if test="${table=='add'}">
    <%@ include file="shoppingCart.jsp" %>
</c:if>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Bootstrap core CSS -->
    <link href="../../resource/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="../../resource/css/dashboard.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="../../resource/css/dashboard.css" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>


<nav class="navbar navbar-inverse sticky-top flex-md-nowrap p-0">
    <a class=" brand navbar-brand text-light">
        <img src="${contextPath}/resource/images/conHp.png" width="30" height="30" class="d-inline-block align-top"
             alt="">
        Spring Line</a>

    <div class="navbar-nav mr-auto">
        <li class="nav-item"><a class="text-secondary" href="/logout">Logout</a></li>
    </div>
    <c:if test="${table=='add'}">
        <ul class=" my-2 my-lg-0">
            <li id="cd-cart-trigger" class="nav-item"/><a class="cd-img-replace" href="#0"></a></li>
        </ul>
    </c:if>

    <!--end navbar-right -->

</nav>

