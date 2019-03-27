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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="elements/TopNavBar.jsp" %>
<%@ include file="elements/SideBar.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="urlPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<html>
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="../resource/images/favicon1.ico">

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="${contextPath}/resource/dist/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="${contextPath}/resource/css/dashboard.css"/>" rel="stylesheet">

    <script src="../resource/js/ComboBox.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../resource/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="${contextPath}/resource/dist/js/bootstrap.min.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
    <title>Options</title>
    <script>
        var flag = true;
        var priceTotal =${contractDTO.tariff.price};
        document.getElementById("totalMonthlyPrice").innerHTML = "$" + priceTotal;
        $(document).ready(function () {
          //  checksBasket()
            var checkboxes = document.getElementsByClassName('myClass');
            for (var i = 0; i < checkboxes.length; i++) {
                var value = checkboxes[i].value;
                if (checkboxes[i].checked) {
                    var children = document.getElementById(value);
                    children.style.display = "block";
                }
            }
        });
        $(document).ready(function () {
         // radioBasket();
            var checkboxes = document.getElementsByClassName('myClass1');
            for (var i = 0; i < checkboxes.length; i++) {
                var value = checkboxes[i].value;
                if (checkboxes[i].checked) {
                    var children = document.getElementById(value);
                    children.style.display = "block";
                }
            }
        });
        flag = false;
    </script>
</head>
<body>
<div class="container-fluid">

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

        <h1 class="page-header">Choose options ${urlPath}</h1>
        <h2 class="table-title">${message}</h2>

        <c:choose>
            <c:when test="${NEB=='yes'}">
                <form action="/newContract/client" method="post" command="contract">
                    <c:forEach items="${optionGroups}" var="og">
                        <%@ include file="elements/ComboBox element.jsp" %>
                        <br>
                    </c:forEach>
                    <div id="radios">
                        <input type="hidden" class='generated3'>
                    </div>
                    <c:forEach items="${options}" var="o">
                        <c:set var="flag" value="true" scope="page"/>
                        <c:forEach items="${optionGroups}" var="og">
                            <c:if test="${o.optionGroup.id==og.id}">
                                <c:set var="flag" value="false" scope="page"/>
                            </c:if>
                        </c:forEach>
                        <c:if test="${flag==true}">
                            <%@ include file="elements/Card.jsp" %>
                            <br>
                        </c:if>
                    </c:forEach>
                    <div class="row">
                        <div class="col-sm-1 form-group">
                            <button type="submit" class="btn btn-success" name="action" value="new">New Client <i
                                    class="glyphicon glyphicon-plus"></i>
                            </button>
                        </div>
                        <div class="form-group col-sm-1">
                            <button type="submit" class="btn btn-success" name="action" value="existing">Existing
                                client <i class="glyphicon glyphicon-user"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <form action="${urlPath}" method="post" command="contract">
                    <c:forEach items="${optionGroups}" var="og">
                        <%@ include file="elements/ComboBox element.jsp" %>
                        <br>
                    </c:forEach>
                    <div id="radios">
                        <input type="hidden" class='generated3'>
                    </div>
                    <c:forEach items="${options}" var="o">
                        <c:set var="flag" value="true" scope="page"/>
                        <c:forEach items="${optionGroups}" var="og">
                            <c:if test="${o.optionGroup.id==og.id}">
                                <c:set var="flag" value="false" scope="page"/>
                            </c:if>
                        </c:forEach>
                        <c:if test="${flag==true}">
                            <%@ include file="elements/Card.jsp" %>
                            <br>
                        </c:if>
                    </c:forEach>
                    <div class="row">
                        <button type="submit" class="btn btn-success" name="action" value="new">Next <i
                                class="glyphicon glyphicon-chevron-right"></i></button>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>


    </main>

</div>

<script>
    $('input.chk').on('change', function () {
        checkChange();
        checksBasket(${contractDTO.tariff.price});
    });
</script>

<script>
    $('.radio').on('change', function () {
        radioChange();
       radioBasket(${contractDTO.tariff.price});
    });
</script>


</body>
</html>
