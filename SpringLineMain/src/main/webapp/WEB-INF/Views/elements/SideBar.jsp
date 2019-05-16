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
    <link href="${contextPath}/resource/css/dashboard.css" rel="stylesheet">
    <title>SideNavBAr</title>>
</head>

<nav class="col-sm-3 col-md-2 sidebar">
    <div class="sidebar-sticky">

        <security:authorize access="hasRole('ROLE_ADMIN')">
        <h6 class="sidebar-heading d-flex justify-content-between mt-4 mb-1 text-muted">
                <span>ADMINISTRATION</span>
            </h6>
        <ul class="nav flex-column" id="nav">
            <li class="nav-item"><a class="text-success" href="${contextPath}/tariffs/">Tariffs </a></li>
            <li class="nav-item"><a class="text-success" href="${contextPath}/options/">Options</a></li>
            <li class="nav-item"><a class="text-success" href="${contextPath}/optionGroups/">Option Groups</a></li>
        </ul>
        <h6 class="sidebar-heading d-flex justify-content-between mt-4 mb-1 text-muted">
            <span>BUSINESS PROCESSES</span>
        </h6>
        <ul class="nav flex-column">
            <li class="nav-item"><a class="text-success" href="${contextPath}/newContract/tariffs">New Contract</a></li>
            <li class="nav-item"><a class="text-success" href="${contextPath}/users/">Existing Customers</a></li>
        </ul>
        </security:authorize>
        <security:authorize access="hasRole('ROLE_USER')">
            <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                <span>CONTRACT DETAILS</span>
            </h6>
            <c:forEach items="${client.contracts}" var="c">
                <ul>
                <li class="nav-item"><a href="${contextPath}/existingContract/${c.phoneNumber}">${c.phoneNumber}</a></li>
                </ul>
            </c:forEach>
        </security:authorize>
    </div>
</nav>







