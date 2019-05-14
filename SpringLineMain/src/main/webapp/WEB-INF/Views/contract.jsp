<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 03.03.2019
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="elements/TopNavBar.jsp" %>

<c:set var="urlPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="../resource/images/favicon1.ico">
    <script src="${contextPath}/resource/js/pagination.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
    <title>Client details</title>
    <link href="${contextPath}/resource/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<%@ include file="elements/SideBar.jsp" %>
<div class="container-fluid">
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

        <c:choose>
            <c:when test="${table=='add'}">
                <h1 class="page-header">Confirmation page</h1>
            </c:when>
            <c:otherwise>
                <h1 class="page-header">Contract details</h1>
            </c:otherwise>
        </c:choose>
        <h2 class="page-header text-danger">${error}</h2>
        <c:if test="${error==null}">
            <%@ include file="customerData.jsp" %>
            <br>
            <%@ include file="contractData.jsp" %>

            <c:choose>
                <c:when test="${table=='add'}">
                    <form class="form-group" action="${urlPath}/true" method="post">
                        <label class="control-label">Send bills by email: </label>
                        <input type="checkbox" class="chk" name="email" id="email"/>
                        <row>
                            <br>
                            <button class="btn btn-success" onclick="window.print()">Print <em class="fas fa-print"></em>
                            </button>
                            <button type="submit" class="btn btn-success">Confirm <em class="far fa-save"></em></button>
                            <a type="button" href="${contextPath}/welcome"
                               class="btn btn-danger">Cancel <em class="fas fa-times"></em></a>
                        </row>
                    </form>
                </c:when>
                <c:otherwise>
                    <div class="row">
                        <div class="form-group">

                            <c:choose>
                                <c:when test="${contractDto.blocked==true}">
                                    <form class="form-group" action="${contextPath}/existingContract/unblock"
                                          method="get">
                                        <button type="submit" class="btn btn-danger"
                                                <c:if test="${contractDto.agentBlock==true}">
                                                    <security:authorize access="hasRole('ROLE_USER')">
                                                        disabled
                                                    </security:authorize>
                                                </c:if>
                                        >Unblock Sim card <em class="fas fa-unlock"></em>
                                        </button>
                                        <c:if test="${contractDto.agentBlock==true}">
                                            <security:authorize access="hasRole('ROLE_USER')">
                                                <p class="description2"> You should contact shop agent to unblock
                                                    sim-card</p>
                                            </security:authorize>
                                        </c:if>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <a type="button" href="${contextPath}/existingContract/block"
                                       class="btn btn-danger">Block Sim card <em class="fas fa-lock"></em></a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>

    </main>
    <!-- Modal -->
    <div class="modal fade" id="changeTariff" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Change Tariff</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    After changing the tariff all current options will be lost.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Return</button>
                    <a href="${contextPath}/existingContract/tariffs" class="btn btn-success" role="button">Continue</a>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
