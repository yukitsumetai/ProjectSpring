<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 14.03.2019
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<link rel="icon" href="${contextPath}/resource/images/favicon1.png">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="<c:url value="${contextPath}/resource/css/dashboard.css"/>" rel="stylesheet">
</head>

<nav class="col-sm-3 col-md-2 sidebar">
    <div class="sidebar-sticky">
        <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
            <span>ADMINISTRATION</span>
        </h6>
        <ul class="nav flex-column" id="nav">
            <li class="nav-item"><a class="nav-link active" href="${contextPath}/tariffs/">Tariffs </a></li>
            <li class="nav-item"><a href="${contextPath}/options/"><span data-feather="layers"></span>Options</a></li>
            <li class="nav-item"><a href="${contextPath}/optionGroups/">Option Groups</a></li>

        </ul>
        <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
            <span>BUSINESS PROCESSES</span>
        </h6>
        <ul class="nav nav-sidebar">
            <li class="nav-item"><a href="${contextPath}/newContract/tariffs">New Contract</a></li>
            <li class="nav-item"><a href="${contextPath}/existingContract/search">Existing Contract</a></li>
            <li class="nav-item"><a href="${contextPath}/users/">Customers</a></li>
        </ul>
        <ul class="nav nav-sidebar">
            <li><a href="">Contract Details</a></li>
            <li><a href="">Change Tariff</a></li>
            <li><a href="">Manage Options</a></li>
            <li><a href="">Change Tariff</a></li>
        </ul>
    </div>
</nav>







