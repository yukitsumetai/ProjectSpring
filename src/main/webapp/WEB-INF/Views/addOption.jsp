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
<%@ include file="elements/TopNavBar.jsp" %>
<%@ include file="elements/SideBar.jsp" %>
<c:set var="urlPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<html>
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="../resource/images/favicon1.ico">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../resource/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../resource/dist/js/bootstrap.min.js"></script>
    <script src="../resource/js/ComboBox.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.js"></script>

    <title>Client details</title>

    <!--Pagination dropdown-->
    <script>
        $(document).ready(function () {
            return Validate();
        });
    </script>

</head>

<body>
<div class="container-fluid">
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
        <h1 class="page-header">Option administration</h1>


        <div id="personal data">
            <form:form method="post" action="/options/new" modelAttribute="option">
                <div class="table-title">
                    <h2>Add option details</h2>
                </div>
                <div class="form-group">
                    <label class="control-label">Option Name*</label>
                    <input id="name" type="text" name="name" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label class="control-label">One Time Price*</label>
                    <input id="priceOneTime" type="number" name="priceOneTime"
                           class="form-control" required/>
                </div>
                <div class="form-group">
                    <label class="control-label">Monthly Price*</label>
                    <input id="priceMonthly" type="number" name="priceMonthly"
                           class="form-control" required/>
                </div>
                <div class="form-group">
                    <label class="control-label">Description</label>
                    <textarea row="4" id="Description" type="text" name="Description"
                              class="form-control"></textarea>
                </div>
                <div class="form-group">
                    <label class="control-label">Validity: </label>
                    <input type="checkbox" class="chk" name="isValid" id="isValid" value=true checked/>
                </div>
                <input type="hidden" name="relation" id="relation" value="alone"/>

                <div id="comTariffs" style="display:block">
                    <div id="table-wrapper">
                        <div class="table-title">
                            <h2>Set relation with existing tariffs/options</h2>
                        </div>
                    </div>
                    <div class="btn-group btn-group-toggle btn-success" data-toggle="buttons">

                        <label class="btn btn-success active">
                            <input type="radio" class="radio" name="options" value="alone" id="option1"
                                   autocomplete="off"
                                   checked> Stand alone
                        </label>
                        <label class="btn btn-success">
                            <input type="radio" class="radio" name="options" value="parent" id="option2"
                                   autocomplete="off">
                            Has parent
                        </label>
                        <label class="btn btn-success">
                            <input type="radio" class="radio" name="options" value="children" id="option3"
                                   autocomplete="off"> Has children
                        </label>
                    </div>
                    <br> <br>
                    <!-- Dropdown -->
                    <div class="form-group">
                        <label class="control-label">Select option group: </label>
                        <select  class="form-control"
                                id="select1" name="group" onscroll="scrolled(this)" data-live-search="true" onfocus='this.size=5;' onblur='this.size=1;' onchange='this.size=1;' this.blur();>
                            <option value="0">None</option>
                        </select>
                        <p class="description2" id="selectForm" style="display:none">(Supplementary options cannot
                            belong to option group)</p>
                        <input type="hidden" id="page" value=1>
                    </div>

                    <div class="form-group">
                        <label class="control-label">Has compatible tariffs: </label>
                        <input type="checkbox" class="chk2" name="tariffs" id="tariffs" value="false"/>&nbsp;
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-success">Add
                    </button>
                </div>

            </form:form>
        </div>
    </main>

</div>


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
    var state="";
    $('input.radio').on('change', function () {

        radiobutton(state);
    });
</script>

</body>
</html>