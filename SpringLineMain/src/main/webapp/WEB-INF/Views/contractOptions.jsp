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
<!DOCTYPE html>
<html>
<head>

<%--    <link href="${contextPath}/resource/dist/css/bootstrap.min.css" rel="stylesheet">--%>
    <script src="${contextPath}/resource/js/options.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
    <title>Options</title>
    <script>
        var flag = true;
        $(document).ready(function () {
            var checkboxes = document.getElementsByClassName('myClass');
            for (var i = 0; i < checkboxes.length; i++) {
                var value = checkboxes[i].value;
                if (checkboxes[i].checked) {
                    var children = document.getElementById(value);
                    if (children!=null){
                        children.style.display = "block";}
                }
            }
        });
        flag = false;
    </script>
    <script>
        $(document).ready(function () {
            var checkboxes = document.getElementsByClassName('myClass1');
            for (var i = 0; i < checkboxes.length; i++) {
                var value = checkboxes[i].value;
                if (checkboxes[i].checked) {
                    var children = document.getElementById(value);
                    if (children!=null){
                        children.style.display = "block";
                    }
                }}
        });
    </script>
    <script>
        $(document).ready(function () {
            radioBasket(${contractDto.tariff.price});
            checksBasket(${contractDto.tariff.price});
        });
    </script>
</head>
<body>
<div class="container-fluid">

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

        <h1 class="page-header">Choose options</h1>
        <h2 class="table-title">${message}</h2>

        <c:choose>
            <c:when test="${NEB=='yes'}">
                <form action="${contextPath}/newContract/client" method="post" command="contract">
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
                    <row>
                            <button type="submit" class="btn btn-success" name="action" value="new">New Client <em class="fas fa-user-plus"></em>
                            </button>
                            <button type="submit" class="btn btn-success" name="action" value="existing">Existing client <em class="fas fa-user-edit"></em>
                            </button>
                    </row>
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
                        <button type="submit" class="btn btn-success" name="action" value="new">Next  <em class="fas fa-arrow-right"></em></button>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>


    </main>

</div>

<script>
    $('input.chk').on('change', function () {
        checkChange('chk');
        checksBasket(${contractDto.tariff.price});
    });
</script>

<script>
    $('.radio').on('change', function () {
        checkChange('radio');
       radioBasket(${contractDto.tariff.price});
    });
</script>


</body>
</html>
