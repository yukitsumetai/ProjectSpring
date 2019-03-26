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


<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js"></script>
    <link href="<c:url value="${contextPath}/resource/dist/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="${contextPath}/resource/css/dashboard.css"/>" rel="stylesheet">
    <title>Tariffs Overview</title>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="${contextPath}/resource/dist/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resource/js/pagination.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>


<script>
        $(document).ready(function () {
            $("#myInput").on("keyup", function () {
                var value = $(this).val().toLowerCase();
                $("#myTable tr").filter(function () {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                });
            });
        });
    </script>
</head>

<body>
<%@ include file="elements/TopNavBar.jsp" %>
<div class="container-fluid">

    <%@ include file="elements/SideBar.jsp" %>

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
                            <button type="submit" class="btn btn-success">Next <i
                                    class="glyphicon glyphicon-chevron-right"></i></button>
                        </div>
                    </form>
                </c:when>
                <c:when test="${table=='add'}">
                    <form action="/newContract/options" method="post">
                        <%@ include file="tableTariffs.jsp" %>
                        <div>
                            <button type="submit" class="btn btn-success">Next <i
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
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
                Existing relations with options will be lost.
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                <form id="action" action="">
                    <button type="submit" class="btn btn-success">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%-- Modal Script --%>
<script>
    $('#deleteModal').on('show.bs.modal', function (e) {

        //get data-id attribute of the clicked element
        var id = $(e.relatedTarget).data('id');

        //populate the textbox
        $(e.currentTarget).find('form[id="action"]').val(id);
        var $form = $('#action');
        $form.attr('action', '/tariffs/delete/' + id);
    });
</script>
<%--Modal delete refused--%>
<c:if test="${refusedDelete=='yes'}">
    <script type="text/javascript">
        $(window).on('load', function () {
            $('#deleteRefusedModal').modal('show');
        });
    </script>
</c:if>

<%--Alert--%>
<script type="text/javascript">
    function validate() {
        if (document.getElementById('tariffID').checked) {
        } else {
            alert("You didn't choose any tariff!");
        }
    }
</script>
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
                var name = this.getAttribute('tariffName');
                var price = this.getAttribute('price');
                document.getElementById("tariffName").innerHTML = name;
                document.getElementById("tariffPrice").innerHTML = "$" + price;
            });
        </script>
    </c:otherwise>
</c:choose>
<%-- Highlight clicked --%>
<script>
    $(document).ready(function () {
        $('td :checkbox').bind('change checked', function () {
            $(this).closest('tr').toggleClass('highlight', this.checked);
        }).change();
    });
</script>

</body>
</html>