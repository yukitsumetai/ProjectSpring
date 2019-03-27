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
<html>
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="../resource/images/favicon1.ico">

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="${contextPath}/resource/dist/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="${contextPath}/resource/css/dashboard.css"/>" rel="stylesheet">


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../resource/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="${contextPath}/resource/dist/js/bootstrap.min.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
    <title>Client details</title>
</head>
<body>
<div class="container-fluid">
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
        <h1 class="page-header">Tariff administration</h1>

        <div id="option data">
            <form id="simpleForm" action="/tariffs/new" method="post">
                <div class="table-title">
                    <h2>Add tariff details</h2>
                </div>
                <div class="form-group">
                    <label class="control-label">Tariff Name*</label>
                    <input id="name" type="text" name="name" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label class="control-label">Monthly Price*</label>
                    <input id="price" type="number" name="price" class="form-control" required/>
                </div>

                <div class="form-group">
                    <label class="control-label">Description</label>
                    <textarea row="'4" id="Description" type="text" name="Description" class="form-control"></textarea>
                </div>
                <div class="form-group">
                    <label class="control-label">Validity: </label>
                    <input type="checkbox" class="chk"  name="isValid" id="isValid" value=true checked/>&nbsp;
                </div>


                <div class="form-group comOptions" id="comOptions" style="display:block">
                    <label class="control-label">Set compatible options: </label>
                    <input type="checkbox" class="chk" name="compatibleOptions" id="options" value=false/>&nbsp;
                </div>


                <div class="form-group">
                    <button type="submit" class="btn btn-success">Add <i
                            class="glyphicon glyphicon-plus"></i>
                    </button>
                </div>

            </form>

        </div>
    </main>

</div>


<!-- Checkbox options -->
<script type="text/javascript">
    $(document).ready(function () {
        $('#options').change(function () {
            if ($(this).is(":checked")) {
                this.value = true;
            } else this.value = false;
        });
    });
</script>

</body>
</html>
