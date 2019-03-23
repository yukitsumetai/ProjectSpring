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
<%@ include file="TopNavBar.jsp" %>
<%@ include file="SideBar.jsp" %>


<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" href="../resource/images/favicon1.ico">

    <title>Tariffs Overview</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../resource/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../resource/dist/js/bootstrap.min.js"></script>
    <script src="../resource/js/pagination.js"></script>
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

<div class="container-fluid">

    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
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
                <form action="/existingContract/tariffChange" method="post">
                    <%@ include file="tableTariffs.jsp" %>
                    <div>
                        <button type="submit" class="btn btn-success">Next</button>
                    </div>
                </form>
                </c:when>
                <c:when test="${table=='add'}">
                    <form action="/newContract/options" method="post">
                        <%@ include file="tableTariffs.jsp" %>
                        <div>
                            <button type="submit" class="btn btn-success">Next</button>
                        </div>
                    </form>
                </c:when>
                <c:when test="${table=='option'}">
                    <form action="/options/new/tariffs" method="post">
                        <%@ include file="tableTariffs.jsp" %>
                        <div>
                            <button type="submit" class="btn btn-success">Next</button>
                        </div>
                    </form>
                </c:when>
                <c:otherwise>
                    <%@ include file="tableTariffs.jsp" %>
                </c:otherwise>
                </c:choose>


        </div>
    </div>
</div>


<!-- Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel"
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
                Tariff status will be set to invalid and it won't be able to use tariffs in new contracts
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                <form id="action" action="">
                    <button type="submit" class="btn btn-primary">Yes</button>
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
        $(window).on('load',function(){
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