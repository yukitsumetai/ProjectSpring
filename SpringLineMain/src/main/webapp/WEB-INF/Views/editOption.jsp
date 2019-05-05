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
    <script src="${contextPath}/resource/js/dropdownAjax.js"></script>
    <script src="${contextPath}/resource/js/options.js"></script>
    <title>Client details</title>
    <script>
        $(document).ready(function () {
            return Validate();
        });
    </script>
    <script>
        $(document).ready(function () {
            var state = "";
            <c:if test="${option.parent!=null}">
            state = "children";
            </c:if>
            radiobutton(state);
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
        <h1 class="page-header">Option administration</h1>

        <div id="option data">
            <form id="optionForm" action="${contextPath}/options/edit" method="post">
                <div class="table-title">
                    <h2>Edit option details</h2>
                </div>
                <div class="form-group">
                    <label class="control-label">Option Name</label>
                    <input id="name" value="${option.name}" type="text" name="name" class="form-control" disabled/>
                </div>
                <div class="form-group">
                    <label class="control-label">One Time Price</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">$</span>
                        </div>
                        <input id="priceOneTime" type="number" value="${option.priceOneTime}" step="0.01" name="priceOneTime" class="form-control" disabled/>
                    </div>

                </div>
                <div class="form-group">
                    <label class="control-label">Monthly Price</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">$</span>
                        </div>
                        <input id="priceMonthly" type="number" value="${option.priceMonthly}" step="0.01" name="priceMonthly" class="form-control" disabled/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label">Description</label>
                    <textarea row="4" id="Description" type="text" name="Description" maxlength="180"
                              class="form-control">${option.description}</textarea>
                </div>
                <div class="form-group">
                    <label class="control-label">Validity: </label>
                    <input type="checkbox" class="chk" name="isValid" id="isValid" value=true
                            <c:if test="${option.isValid==true}">
                                checked disabled
                            </c:if>/>
                </div>
                <input type="hidden" name="relation" id="relation" value="nothing"/>

                    <div id="table-wrapper">
                        <div class="table-title">
                            <h2>Set relation with existing tariffs/options</h2>
                        </div>
                    </div>

                    <div class="table-title">
                        <b>Current state:</b><br>
                        Parent: ${option.parent.name}<br>
                        Children:${fn:length(option.children)}
                    </div>

                    <div class="btn-group btn-group-toggle btn-success" data-toggle="buttons">

                        <label class="btn  btn-success active">
                            <input type="radio" class="radio" name="options" value="nothing" id="option0"
                                   autocomplete="off"> Do nothing
                        </label>
                        <label class="btn  btn-success">
                            <input type="radio" class="radio" name="options" value="alone" id="option1"
                                   autocomplete="off"> Set stand alone
                        </label>
                        <label class="btn  btn-success">
                            <input type="radio" class="radio" name="options" value="parent" id="option2"
                                   autocomplete="off" >
                            Change/set parent
                        </label>
                        <label class="btn  btn-success">
                            <input type="radio" class="radio" name="options" value="children" id="option3"
                                   autocomplete="off"> Change/set children
                        </label>
                    </div>
                    <!-- Dropdown -->
                    <div class="form-group">
                        <label class="control-label">Select option group: </label>
                        <select class="form-control"
                                id="select1" name="group" onscroll="scrolled(this, ${option.optionGroup.id})" data-live-search="true"
                                onfocus='this.size=5;' onblur='this.size=1;' onchange='this.size=1;' this.blur();>
                            <option value="0">None</option>
                            <c:if test="${option.optionGroup!=null}">
                                <option value="${option.optionGroup.id}" selected>${option.optionGroup.name}</option>
                            </c:if>
                        </select>
                        <p class="description2" id="selectForm" style="display:none">(Supplementary options cannot
                            belong to option group)</p>
                        <input type="hidden" id="page" value=1>
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
    var state = "";
    <c:if test="${option.parent!=null}">
    state = "children";
    </c:if>
    $('input.radio').on('change', function () {

        radiobutton(state);
    });
</script>

</body>
</html>
