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
<%@ include file="TopNavBar.jsp" %>
<%@ include file="SideBar.jsp" %>
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
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <h1 class="page-header">Option administration</h1>
        <div class="table-title">
            <h2>Edit option details</h2>
        </div>
        <div id="option data">
            <form id="optionForm" action="/options/edit" method="post">

                <div class="form-group">
                    <label class="control-label">Option Name</label>
                    <input id="name" value="${option.name}" type="text" name="name" class="form-control" disabled/>
                </div>
                <div class="form-group">
                    <label class="control-label">One Time Price</label>
                    <input id="priceOneTime" value="${option.priceOneTime}" type="number" name="priceOneTime"
                           class="form-control" disabled/>
                </div>
                <div class="form-group">
                    <label class="control-label">Monthly Price</label>
                    <input id="priceMonthly" value="${option.priceMonthly}" type="number" name="priceMonthly"
                           class="form-control" disabled/>
                </div>
                <div class="form-group">
                    <label class="control-label">Description</label>
                    <textarea row="4" id="Description"  type="text" name="Description"
                              class="form-control">${option.description}</textarea>
                </div>
                <div class="form-group">
                    <label class="control-label">Validity: </label>
                    <input type="checkbox" class="chk" name="isValid" id="isValid" value=true
                            <c:if test="${option.isValid==true}">
                                checked
                            </c:if>
                    />&nbsp;
                </div>

                <div id="table-wrapper">
                    <div class="table-title">
                        <h2>Set relation with existing tariffs/options</h2>
                    </div>
                </div>

                    <div class="table-title">
                        Current state:<br>
                        Parent: ${option.parent.name}<br>
                        Children:${fn:length(option.children)}
                    </div>


                <div class="btn-group btn-group-toggle" data-toggle="buttons">
                    <input type="hidden" name="relation" id="relation" value="nothing"/>
                    <label class="btn btn-secondary active">
                        <input type="radio" class="radio" name="options" value="nothing" id="option0" autocomplete="off"> Do nothing
                    </label>
                    <label class="btn btn-secondary">
                        <input type="radio" class="radio" name="options" value="alone" id="option1" autocomplete="off"> Set stand alone
                    </label>
                    <label class="btn btn-secondary">
                        <input type="radio" class="radio" name="options" value="parent" id="option2" autocomplete="off">
                        Change/set parent
                    </label>
                    <label class="btn btn-secondary">
                        <input type="radio" class="radio" name="options" value="children" id="option3"
                               autocomplete="off"> Change/set children
                    </label>
                </div>
                <div class="form-group">
                    <br>
                    <label class="control-label">Belongs to option group: </label>
                    <input type="checkbox" class="chk" name="group" id="group" value="true"/>&nbsp;
                </div>
                <div class="form-group">
                    <label class="control-label">Change compatible tariffs: </label>
                    <input type="checkbox" class="chk2" name="tariffs" id="tariffs" value="false"/>&nbsp;
                </div>


                <div class="form-group">
                    <button type="submit" class="btn btn-success"
                            onclick="return Validate()">Next
                    </button>
                </div>

            </form>
        </div>
    </div>

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
        $('#tariffs').change(function () {
            if ($(this).is(":checked")) {
                this.value = true;
            } else this.value = false;
        });
    });
</script>
<!-- Checkbox group -->
<script type="text/javascript">
    $(document).ready(function () {
        $('#chk').change(function () {
            if ($(this).is(":checked")) {
                this.value = true;
            } else this.value = false;
        });
    });
</script>
<!-- Parents -->
<script type="text/javascript">
    $('input.radio').on('change', function () {
        var radios = document.getElementsByName('options');
        for (var i = 0, length = radios.length; i < length; i++) {
            if (radios[i].checked) {
                document.getElementById("relation").value = radios[i].value;
                break;
            }
        }
    });
</script>

</body>
</html>
