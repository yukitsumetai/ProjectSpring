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

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="../resource/images/favicon1.ico">

    <!-- Bootstrap core CSS -->
    <link href="${contextPath}/resource/dist/css/bootstrap.min.css" } rel="stylesheet">
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
            <form:form method="post" action="${contextPath}/tariffs/edit" modelAttribute="tariff">
                <div class="table-title">
                    <h2>Edit tariff details</h2>
                </div>
                <div class="form-group">
                    <label class="control-label">Tariff Name: </label>
                    <input value="${tariff.name}" id="name" type="text" name="name" class="form-control" disabled/>
                </div>
                <div class="form-group">
                    <label class="control-label">Monthly Price</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">$</span>
                        </div>
                        <input id="price" type="number" step="0.01" name="price" value="${tariff.price}"
                               class="form-control" disabled/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label">Description</label>
                    <textarea rows="4" id="Description" type="text" maxlength="180" name="Description" onblur="requiredField(this)"
                              class="form-control">${tariff.description}</textarea>
                </div>
                <div class="form-group">
                    <label class="control-label">Validity: </label>
                    <input type="checkbox" class="chk" name="isValid" id="isValid"
                           value="${tariff.isValid}"
                            <c:if test="${tariff.isValid==true}">
                                checked disabled
                            </c:if>/>&nbsp;
                </div>
                <c:if test="${tariff.isValid==true}">
                <div class="form-group promotion" id="promotion" style="display:block">
                    <label class="control-label">Promotion: </label>
                    <input type="checkbox" class="chk" name="isPromoted" id="isPromoted"
                            <c:if test="${tariff.promoted==true}">
                                checked
                            </c:if>&nbsp;/>
                </div>
                </c:if>
                <div class="form-group">
                    <input type="hidden" name="initialState"
                           value="${tariff.promoted}">
                </div>
                <div class="form-group">
                    <label class="control-label">Set/change compatible options: </label>
                    <input type="checkbox" class="chk" name="compatibleOptions" id="options" value=false/>&nbsp;
                </div>

                <div class="form-group">
                    <br>
                    <button type="submit" class="btn btn-success">Change <em class="far fa-edit"></em>
                    </button>
                </div>

            </form:form>
        </div>
    </main>

</div>

<!-- Valid -->
<script type="text/javascript">
    $(document).ready(function () {
        isValidChange();
        isValidChangePromoted();
        addOptionsChange();
    });
</script>
</body>
</html>
