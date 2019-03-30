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


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ include file="elements/TopNavBar.jsp" %>
<%@ include file="elements/SideBar.jsp" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js"></script>
    <link href="<c:url value="${contextPath}/resource/dist/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="${contextPath}/resource/css/dashboard.css"/>" rel="stylesheet">
    <title>Tariffs Overview</title>
    <
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <script src="${contextPath}/resource/js/pagination.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>

    <%-- Modal Script --%>
    <script>
        $(document).on('shown.bs.modal', '#deleteModal', function (e) {
            //get data-id attribute of the clicked element
            var id = $(e.relatedTarget).data('id');

            //populate the textbox
            $(e.currentTarget).find('form[id="action"]').val(id);
            var $form = $('#action');
            $form.attr('action', '/tariffs/delete/' + id);
        });
    </script>
</head>

<body>

<div class="container-fluid">

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
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
                    <div class="col-sm-6">

                        <div class="search-box">
                            <i class="material-icons">&#xE8B6;</i>
                            <input type="text" class="form-control" id="myInput" placeholder="Search&hellip;">
                        </div>
                        <div class="newtariff">

                            <c:if test="${table=='edit'}">
                                <form action="/tariffs/new">
                                    <button type="submit" class="btn btn-success">Add Tariff</button>
                                </form>
                            </c:if>

                        </div>
                    </div>

                </div>
            </div>


            <c:choose>

                <c:when test="${NEB=='no'}">
                    <form action="/existingContract/tariffs" method="post">
                        <%@ include file="tableTariffs.jsp" %>
                        <div>
                            <button type="submit" class="btn btn-success" id="options" disabled>Next <i
                                    class="glyphicon glyphicon-chevron-right"></i></button>
                        </div>
                    </form>
                </c:when>
                <c:when test="${table=='add'}">
                    <form action="/newContract/options" method="post">
                        <%@ include file="tableTariffs.jsp" %>
                        <div>
                            <button type="submit" class="btn btn-success" id="options" disabled>Next <i
                                    class="glyphicon glyphicon-chevron-right"></i></button>
                        </div>
                    </form>
                </c:when>
                <c:when test="${table=='option'}">
                    <form action="/options/new/tariffs" method="post">
                        <%@ include file="tableTariffs.jsp" %>
                        <div>
                            <button type="submit" class="btn btn-success">Save <i
                                    class="glyphicon glyphicon-floppy-disk"></i></button>
                        </div>
                    </form>
                </c:when>
                <c:when test="${table=='optionEdit'}">
                    <form action="/options/edit/tariffs" method="post">
                        <%@ include file="tableTariffs.jsp" %>
                        <div>
                            <button type="submit" class="btn btn-success">Save <i
                                    class="glyphicon glyphicon-floppy-disk"></i></button>
                        </div>
                    </form>
                </c:when>
                <c:otherwise>
                    <%@ include file="tableTariffs.jsp" %>
                </c:otherwise>
            </c:choose>


        </div>
</div>

</main>

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
    <c:when test="${table=='option'}">
    </c:when>
    <c:when test="${table=='optionEdit'}">
    </c:when>
    <c:otherwise>
        <script>
            $('input.chk').on('change', function () {
                $('input.chk').not(this).prop('checked', false);
            });
        </script>
    </c:otherwise>
</c:choose>
<%-- Highlight clicked --%>
<script>
    $('input.chk').on('change', function () {
        $('.generated1').remove();
        var flag=true;
        var priceTotal;
        var checkboxes = document.getElementsByClassName('chk');
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                checkboxes[i].closest('tr').classList.add("highlight");
                var name = checkboxes[i].getAttribute('tariffName');
                var price = '$' + checkboxes[i].getAttribute('price');
                priceTotal=parseFloat(checkboxes[i].getAttribute('price'));
                flag=false;
                document.getElementById("totalMonthlyPrice").innerHTML = "$" + priceTotal;
                var newInput = '<li class="generated1">' + name +
                    '<div class="right">' + price + '</div></li>';
                document.getElementById('tariffCart').insertAdjacentHTML('beforeend', newInput);
                document.getElementById('options').disabled=false;
            } else {
                checkboxes[i].closest('tr').classList.remove("highlight");
            }
        }
        if (flag)  document.getElementById("totalMonthlyPrice").innerHTML = "$" + 0;
        if (flag) document.getElementById('options').disabled=true;
    });
</script>
<!--initial pagination-->
<script>
    $(document).ready(function () {
        var curr = document.getElementById('page').value;
        if(curr==0){
            var table;
            <c:if test="${table=='edit'}">
            var table=1;
            </c:if>
            var id="${id}"
            if (!('hasCodeRunBefore' in localStorage)){
                return optionPagination(1, 1, table, id);}}
        document.getElementById('page').value = 1;
    });
</script>
</body>
</html>