<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 14.03.2019
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="urlPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>


<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="<c:url value="${contextPath}/resource/css/dashboard.css"/>" rel="stylesheet">
</head>

<nav class="col-sm-3 col-md-2 sidebar">
    <div class="sidebar-sticky">

        <security:authorize access="hasRole('ROLE_ADMIN')">
        <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                <span>ADMINISTRATION</span>
            </h6>
        <ul class="nav flex-column" id="nav">
            <li class="nav-item"><a href="${contextPath}/tariffs/">Tariffs </a></li>
            <li class="nav-item"><a href="${contextPath}/options/"></span>Options</a></li>
            <li class="nav-item"><a href="${contextPath}/optionGroups/">Option Groups</a></li>

        </ul>
        <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
            <span>BUSINESS PROCESSES</span>
        </h6>
        <ul class="nav nav-sidebar">
            <li class="nav-item"><a href="${contextPath}/newContract/tariffs">New Contract</a></li>
            <li class="nav-item"><a href="${contextPath}/users/">Existing Customers</a></li>
        </ul>
        </security:authorize>
        <security:authorize access="hasRole('ROLE_USER')">
            <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                <span>CONTRACT DETAILS</span>
            </h6>
            <c:forEach items="${client.contracts}" var="c">
                <li class="nav-item"><a href="../existingContract/${c.phoneNumber}">${c.phoneNumber}</a></li>
            </c:forEach>
        </security:authorize>
    </div>
</nav>







