<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 03.03.2019
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="elements/TopNavBar.jsp" %>
<%@ include file="elements/SideBar.jsp" %>
<c:set var="urlPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!DOCTYPE html>
    <html lang="en">
    <head>
        <script src="${contextPath}/resource/js/ComboBox.js"></script>
        <title>Choose related options</title>

        <!--Search-->
        <script>
            $(document).ready(function () {
                Higlight();
                $("#myInput").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $("#myTable tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                    });
                });
            });
        </script>
    </head>

<body>


<div class="container-fluid">

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
        <h1 class="page-header">Choose related options</h1>
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
                        <c:choose>
                            <c:when test="${relation=='parent' ||relation=='parentEdit'}">
                                <h2>Choose parent</h2>
                            </c:when>
                            <c:otherwise>
                                <h2>Choose children</h2>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-sm-6">

                        <div class="search-box">
                            <i class="material-icons">&#xE8B6;</i>
                            <input type="text" class="form-control" id="myInput" placeholder="Search&hellip;">
                        </div>

                    </div>

                </div>
            </div>


            <c:choose>
                <c:when test="${relation=='children'}">
                    <form action="${urlPath}/children" method="post">
                        <%@ include file="tableOptions.jsp" %>
                        <div class="row">
                            <div class="col-sm-2 form-group">
                                <div class="row">
                                    <button type="submit" class="btn btn-success"
                                    <c:choose>
                                            <c:when test="${tariff==true}">value=true</c:when>
                                            <c:otherwise>value=false</c:otherwise>
                                    </c:choose> name="action">Next
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </c:when>
                <c:when test="${relation=='parent'}">
                    <form action="${urlPath}/parent" method="post">

                        <%@ include file="tableOptions.jsp" %>
                        <div class="row">
                            <button type="submit" class="btn btn-success" <c:choose>
                                <c:when test="${tariff==true}">value=true</c:when>
                                <c:otherwise>value=false</c:otherwise>
                            </c:choose> name="action">Next
                            </button>
                        </div>
                    </form>
                </c:when>
            </c:choose>
            <!--Checkbox-->
            <c:if test="${relation=='children'}">

            </c:if>
            <c:if test="${relation=='parent'}">
                <script>
                    $('input.chk').on('change', function () {
                        $('input.chk').not(this).prop('checked', false);
                    });
                </script>
            </c:if>
        </div>
    </div>
</div>
</div>



</body>
</html>





