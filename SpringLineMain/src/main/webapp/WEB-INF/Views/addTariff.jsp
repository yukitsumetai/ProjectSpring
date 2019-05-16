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

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>

    <link href="${contextPath}/resource/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${contextPath}/resource/js/validation.js"></script>

    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
    <title>Add tariff</title>
</head>
<body>
<%@ include file="elements/SideBar.jsp" %>
<div class="container-fluid">
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
        <h1 class="page-header">Tariff administration</h1>
        <h2 class="page-header text-danger">${error}</h2>
        <div id="option data">
            <form id="simpleForm" action="${contextPath}/tariffs/new" method="post">
                <div class="table-title">
                    <h2>Add tariff details</h2>
                </div>
                <div class="form-group">
                    <label class="control-label">Tariff Name*</label>
                    <input id="name" type="text" name="name" placeholder="Maximum 60 characters..." class="form-control" onblur="requiredField(this)"
                           maxlength="60" required/>
                </div>
                <div class="form-group">
                    <label class="control-label">Monthly Price*</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">$</span>
                        </div>
                        <input id="price" type="number" step="0.01" min="0" name="price" class="form-control" onblur="requiredField(this)"required/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label">Description</label>
                    <textarea row="'4" id="Description" type="text" name="Description" maxlength="180" onblur="requiredField(this)"
                              class="form-control"></textarea>
                </div>
                <div class="form-group">
                    <label class="control-label">Validity: </label>
                    <input type="checkbox" class="chk" name="isValid" id="isValid" value=true checked/>&nbsp;
                </div>

                <div class="form-group">
                    <label class="control-label">Promotion: </label>
                    <input type="checkbox" class="chk" name="isPromoted" id="isPromoted"/>&nbsp;
                </div>

                <div class="form-group comOptions" id="comOptions" style="display:block">
                    <label class="control-label">Set compatible options: </label>
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
