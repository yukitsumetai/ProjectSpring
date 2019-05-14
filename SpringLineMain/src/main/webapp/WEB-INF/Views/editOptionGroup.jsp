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
    <link href="${contextPath}/resource/dist/css/bootstrap.min.css"} rel="stylesheet">
    <link href="${contextPath}/resource/css/dashboard.css" rel="stylesheet">
    <script src="${contextPath}/resource/js/validation.js"></script>

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
            <form method="post" action="${contextPath}/optionGroups/edit" >
                <div class="table-title">
                    <h2>Edit optiongroup details</h2>
                </div>
                <div class="form-group">
                    <label class="control-label">Tariff Name: </label>
                    <input value="${optionGroup.name}" id="name" type="text" name="name" class="form-control" disabled/>
                </div>
                <div class="form-group">
                    <label class="control-label">Description</label>
                    <textarea rows="4" id="Description" type="text" name="Description" maxlength="200" onblur="requiredField(this)"
                              class="form-control">${optionGroup.description}</textarea>
                </div>
                <div class="form-group">
                    <label class="control-label">Validity: </label>
                    <input type="checkbox" class="chk" name="isValid" id="isValid"
                           value="${optionGroup.isValid}"
                            <c:if test="${optionGroup.isValid==true}">
                                checked disabled
                            </c:if>/><span class="description2"> (Invalid option groups cannot include options)</span>
                </div>
                <c:if test="${optionGroup.isValid==true}">
                <div class="form-group">
                    <label class="control-label">Set/change included options: </label>
                    <input type="checkbox" class="chk" name="compatibleOptions" id="options" value=false/>&nbsp;
                </div>
                </c:if>
                <div class="form-group">
                    <br>
                    <button type="submit" class="btn btn-success">Change <em class="far fa-edit"></em>
                    </button>
                </div>

            </form>
        </div>
    </main>

</div>

<!-- Valid -->
<script type="text/javascript">
    $(document).ready(function () {
        $('#isValid').change(function () {
            if ($(this).is(":checked")) {
                this.value = true;
            } else this.value = false;
        });
    });
</script>
<!-- Checkbox tariffs -->
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
