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
<!DOCTYPE html>
<html>
<head>
    <!-- Bootstrap core CSS -->
    <link href="${contextPath}/resource/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${contextPath}/resource/js/validation.js"></script>

    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
    <title>Client details</title>
</head>
<body>
<div class="container-fluid">
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
        <h1 class="page-header">Option Groups administration</h1>
        <h2 class="page-header text-danger">${error}</h2>
        <div id="option data">
            <form id="simpleForm" action="${contextPath}/optionGroups/new" method="post">
                <div class="table-title">
                    <h2>Add option group details</h2>
                </div>
                <div class="form-group">
                    <label class="control-label">Option Group Name*</label>
                    <input id="name" type="text" name="name" class="form-control" maxlength="60" onblur="requiredField(this)" required/>
                </div>
                <div class="form-group">
                    <label class="control-label">Description</label>
                    <textarea row="'4" id="Description" type="text" name="Description" maxlength="200" onblur="requiredField(this)" class="form-control"></textarea>
                </div>
                <div class="form-group">
                    <label class="control-label">Validity: </label>
                    <input type="checkbox" class="chk" name="isValid" id="isValid" value=true checked/><span
                        class="description2"> (Invalid option groups cannot include options)</span>
                </div>


                <div class="form-group comOptions" id="comOptions" style="display:block">
                    <label class="control-label">Include options: </label>
                    <input type="checkbox" class="chk" name="compatibleOptions" id="options" value=false/>&nbsp;
                </div>


                <div class="form-group">
                    <button type="submit" class="btn btn-success">Add <em class="fas fa-plus"></em>
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
            var relation = document.getElementById('comOptions');
            if ($(this).is(":checked")) {
                this.value = true;
                relation.style.display = "block";
                $("#comOptions :input").attr("disabled", false);
            } else {
                this.value = false;
                relation.style.display = "none";
                $("#comOptions :input").attr("disabled", true);
            }
        });
    });
</script>
<!-- Checkbox options -->
<script type="text/javascript">
    $(document).ready(function () {
        $('#options').change(function () {
            this.value = !!$(this).is(":checked");
        });
    });
</script>

</body>
</html>
