<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 03.03.2019
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="elements/TopNavBar.jsp" %>

<c:set var="urlPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<!DOCTYPE html>
<html>
<head>
    <script src="${contextPath}/resource/js/paginationAjax.js"></script>
    <script src="${contextPath}/resource/js/options.js"></script>
    <script src="${contextPath}/resource/js/dropdownAjax.js"></script>
    <script src="${contextPath}/resource/js/clientAjax.js"></script>
    <link href="${contextPath}/resource/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Current Clients</title>
</head>

<body>
<%@ include file="elements/SideBar.jsp" %>
<div class="container-fluid">

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

        <h1 class="page-header">Clients</h1>

        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
                        <h2>Clients administration</h2>
                    </div>
                    <div class="col-sm-6">
                        <div class="row float-right">
                            <div class="search-box">
                                <em class="material-icons">&#xE8B6;</em>
                                <input type="text" class="form-control" id="phoneNumber" placeholder="Search&hellip;">
                            </div>
                            <div class="newtariff ">
                                    <button type="submit" onclick="searchClient(<c:if test="${table=='edit'}">1</c:if>)" class="btn btn-success">Find <em class="fas fa-search"></em></button>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <form action="${contextPath}/newContract/confirmExisting" method="post" command="contract">

                <%@ include file="tables/tableClients.jsp" %>
                <c:if test="${urlPath=='/springLine/newContract/client'}">
                <br><br>
                <div>
                    <div class="form-group">
                        <label class="control-label">Select phone number: </label>
                        <select class="form-control" name="phoneNumber"
                                id="select1" name="group" onscroll="scrolledPhone(this)" data-live-search="true"
                                onfocus='this.size=4;' onblur='this.size=1;' onchange='this.size=1;' this.blur();>
                        </select>
                        <input type="hidden" id="page" value=1>
                    </div>
                </div>

                <div>
                    <button type="submit" id="client" class="btn btn-success" disabled>Next <em class="fas fa-arrow-right"></em></button>
                </div>
                </c:if>

            </form>


        </div>
    </main>
</div>
</div>
<%@ include file="elements/modal.jsp" %>
<!--Checkbox-->
<script>
    $(document).on('click', '.chk', function () {
        var checkboxes = document.getElementsByClassName('chk');
        var flag = true;
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                checkboxes[i].closest('tr').classList.add("highlight");
                document.getElementById('client').disabled = false;
                flag = false;
            } else {
                checkboxes[i].closest('tr').classList.remove("highlight");
                checkboxes[i].checked=false;
            }
        }
        if (flag) document.getElementById('client').disabled = true;
    });
</script>
<!--initial pagination-->
<script>
    $(document).ready(function () {
        var curr = document.getElementById('page').value;
        if (curr == 0) {
            var table;
            <c:if test="${table=='edit'}">
            var table = 1;
            </c:if>
                return pagination(2, 1, table);
        }
        document.getElementById('page').value = 1;
    });
</script>
<!--pagination cart-->
<script>
    $(document).on('click', '.chk', function () {
        $('input.chk').not(this).prop('checked', false);
    });
</script>
<script>
    $(document).on('click', '.chk', function () {
        var checkboxes = document.getElementsByClassName('chk');
        $('.opt2').remove();
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                var value = checkboxes[i].value;
                var newInput = '<input name="clientID2" type="hidden" class="opt2" value="' + value + '">';
                document.getElementById('checked').insertAdjacentHTML('beforeend', newInput);
            }
        }
    });
</script>
<!--PaginationDao dropdown-->
<script>
    $(document).ready(function () {
        return phoneNumber();
    });
</script>


</body>
</html>





