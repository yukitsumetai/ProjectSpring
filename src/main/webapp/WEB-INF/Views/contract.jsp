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
<%@ include file="elements/SideBar.jsp" %>
<c:set var="urlPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<html>
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="../resource/images/favicon1.ico">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="../resource/dist/js/bootstrap.min.js"></script>
    <script src="../resource/js/pagination.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
    <title>Client details</title>
</head>
<body>
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
        <%@ include file="customerData.jsp" %>
        <br>
        <%@ include file="contractData.jsp" %>

        <c:choose>
            <c:when test="${table=='add'}">
                <div class="row">
                    <div class="col-sm-1 form-group">
                        <form class="form-group" action="${urlPath}/true" method="post">
                            <button type="submit" class="btn btn-success">Confirm <i
                                    class="glyphicon glyphicon-floppy-disk"></i></button>
                        </form>
                    </div>
                    <div class="col-sm-1 form-group price">
                        <form class="form-group" action="/welcome">
                            <button type="submit" class="btn btn-danger">Cancel <i
                                    class="glyphicon glyphicon-ban-circle"></i></button>
                        </form>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="form-group">

                            <c:choose>
                                <c:when test="${contractDTO.blocked==true}">
                                <form class="form-group" action="/existingContract/unblock" method="get">
                                    <button type="submit" class="btn btn-danger"
                                                <c:if test="${contractDTO.agentBlock==true}">
                                                    <security:authorize access="hasRole('ROLE_USER')">
                                                    disabled
                                                    </security:authorize>
                                                </c:if>
                                    >Unblock Sim card</button>
                                    <c:if test="${contractDTO.agentBlock==true}">
                                        <security:authorize access="hasRole('ROLE_USER')">
                                         <p class="description2"> You should contact shop agent to unblock sim-card</p>
                                        </security:authorize>
                                    </c:if>
                                </form>
                                </c:when>
                            <c:otherwise>
                                <a type="button" href="/existingContract/block" class="btn btn-danger">Block Sim card</a>
                            </c:otherwise>
                            </c:choose>


                    </div>
                </div>
            </c:otherwise>
        </c:choose>


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
                    <a href="/existingContract/tariffs" class="btn btn-success" role="button">Continue</a>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
