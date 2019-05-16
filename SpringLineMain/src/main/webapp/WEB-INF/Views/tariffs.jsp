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
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="urlPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<%@ include file="elements/TopNavBar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="${contextPath}/resource/js/paginationAjax.js"></script>
    <title>Tariffs Overview</title>
    <link href="${contextPath}/resource/dist/css/bootstrap.min.css" rel="stylesheet">

    <!--initial pagination-->
    <script>
        $(document).ready(function () {
            var curr = document.getElementById('page').value;
            if (curr == 0) {
                var table=0;
                 var parent=false;
                <c:if test="${table=='edit'}">
                table = 1;
                </c:if>
                <c:if test="${table=='add'}">
                parent = true;
                </c:if>
                var id = "${id}"
                if (!('hasCodeRunBefore' in localStorage)) {
                    return pagination(1, 1, table, id, parent);
                }
            }
            document.getElementById('page').value = 1;
        });
    </script>

</head>

<body>

<div class="container-fluid">
    <%@ include file="elements/SideBar.jsp" %>

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
        <input type="hidden" id="page" value=0>

        <c:choose>
            <c:when test="${table=='edit'}">
                <h1 class="page-header">Tariffs</h1>
            </c:when>
            <c:when test="${table=='add'}">
                <h1 class="page-header">New contract</h1>
            </c:when>
            <c:otherwise>
                <h1 class="page-header">Tariffs</h1>
            </c:otherwise>
        </c:choose>

        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
                        <c:choose>
                            <c:when test="${table=='edit'}">
                                <h2>Tariffs administration</h2>
                            </c:when>
                            <c:otherwise>
                                <h2>Choose Tariff</h2>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-sm-6 ">
                        <c:if test="${table=='edit'}">
                            <form action="${contextPath}/tariffs/new">
                                <button type="submit" class="btn btn-success right">Add Tariff <em class="fas fa-plus"></em></button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
            <c:choose>

                <c:when test="${NEB=='no'}">
                    <form action="${contextPath}/existingContract/tariffs" method="post">
                        <%@ include file="tables/tableTariffs.jsp" %>
                        <div>
                            <br><br>
                            <button type="submit" class="btn btn-success" id="options" disabled>Next  <em class="fas fa-arrow-right"></em></button>
                        </div>
                    </form>
                </c:when>
                <c:when test="${table=='add'}">
                    <form action="${contextPath}/newContract/options" method="post">
                        <%@ include file="tables/tableTariffs.jsp" %>
                        <div>
                            <br><br>
                            <button type="submit" class="btn btn-success" id="options" disabled>Next <em class="fas fa-arrow-right"></em></button>
                        </div>
                    </form>
                </c:when>
                <c:when test="${table=='option'}">
                    <form action="${contextPath}/options/new/tariffs" method="post">
                        <%@ include file="tables/tableTariffs.jsp" %>
                        <div>
                            <br><br>
                            <button type="submit" class="btn btn-success">Save <em class="far fa-save"></em></button>
                        </div>
                    </form>
                </c:when>
                <c:when test="${table=='optionEdit'}">
                    <form action="${contextPath}/options/edit/tariffs" method="post">
                        <%@ include file="tables/tableTariffs.jsp" %>
                        <div>
                            <br><br>
                            <button type="submit" class="btn btn-success">Save <em class="far fa-save"></em></button>
                        </div>
                    </form>
                </c:when>
                <c:otherwise>
                    <%@ include file="tables/tableTariffs.jsp" %>
                </c:otherwise>
            </c:choose>


        </div>
    </main>
</div>



<!-- Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Delete Tariff</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure that you want to delete the tariff?<br>
                It would not be able to choose it for contracts.
            </div>
            <div class="modal-footer">
                <form>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                </form>
                <form id="action" action="">
                    <button type="submit" class="btn btn-success">Yes</button>
                </form>

            </div>
        </div>
    </div>
</div>

<!--Cart checkboxes-->
<c:choose>
    <c:when test="${table=='option' || table=='optionEdit'}">
        <script>
            $(document).on('click', '.chk', function () {
                var checkboxes = document.getElementsByClassName('chk');
                for (var i = 0; i < checkboxes.length; i++) {
                    if (checkboxes[i].checked) {
                        var value = checkboxes[i].value;
                        var flag = true;
                        var existing = document.getElementsByClassName('opt2');
                        if (existing != null) {
                            for (var j = 0; j < existing.length; j++) {
                                if (existing[j].value == checkboxes[i].value) flag = false;
                            }
                        }
                        if (flag) {
                            var newInput = '<input name="tariffID2" type="hidden" class="opt2" value="' + value + '">';
                            document.getElementById('checked').insertAdjacentHTML('beforeend', newInput);
                        }
                    } else {
                        var existing = document.getElementsByClassName('opt2');
                        if (existing != null) {
                            for (var j = 0; j < existing.length; j++) {
                                if (existing[j].value == checkboxes[i].value) existing[j].remove();
                            }
                        }
                    }
                }
            });
        </script>
    </c:when>
    <c:otherwise>
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
                        var newInput = '<input name="tariffID2" type="hidden" class="opt2" value="' + value + '">';
                        document.getElementById('checked').insertAdjacentHTML('beforeend', newInput);
                    }
                }
            });
        </script>
    </c:otherwise>
</c:choose>
<%-- Highlight clicked --%>
<script>
    $(document).on('click', '.chk', function () {
        $('.generated1').remove();
        var flag = true;
        var priceTotal;
        var checkboxes = document.getElementsByClassName('chk');
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                var name = checkboxes[i].getAttribute('tariffName');
                var price = '$' + checkboxes[i].getAttribute('price');
                priceTotal = parseFloat(checkboxes[i].getAttribute('price'));
                flag = false;
                document.getElementById("totalMonthlyPrice").innerHTML = "$" + priceTotal;
                var newInput = '<li class="generated1">' + name +
                    '<div class="right">' + price + '</div></li>';
                document.getElementById('tariffCart').insertAdjacentHTML('beforeend', newInput);
                document.getElementById('options').disabled = false;
            }
            else {checkboxes[i].closest('tr').classList.remove("highlight")}
        }
        if (flag) document.getElementById("totalMonthlyPrice").innerHTML = "$" + 0;
        if (flag) document.getElementById('options').disabled = true;
    });
</script>


</body>
</html>