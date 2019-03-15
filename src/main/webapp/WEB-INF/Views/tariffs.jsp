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

<%@ include file="TopNavBar.jsp" %>
<%@ include file="SideBar.jsp" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../resource/images/favicon1.ico">

    <title>Tariffs Overview</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../resource/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../resource/dist/js/bootstrap.min.js"></script>
    <script src="../resource/js/pagination.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('[data-toggle="tooltip"]').tooltip();
        });
    </script>

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
            <c:otherwise >
                <h2>New contract</h2>
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
                    <div class="col-sm-4">

                        <div class="search-box">
                            <i class="material-icons">&#xE8B6;</i>
                            <input type="text" class="form-control" id="myInput" placeholder="Search&hellip;">
                        </div>
                        <div class="newtariff col-sm-2">
                            <c:choose>
                                <c:when test="${table=='edit'}">
                                    <form action="/tariffs/new">
                                        <button type="submit" class="btn btn-success">Add Tariff</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form action="/contract/options">
                                        <button type="submit" class="btn btn-success">Next</button>
                                    </form>
                                </c:otherwise>
                            </c:choose>

                    </div>
                    </div>

                </div>
            </div>
            <table class="table table-striped table-hover table-bordered" id="Table">
                <thead>
                <tr>
                    <th onclick="sortTable(0)">Name<i class="fa fa-sort"></i></th>
                    <th onclick="sortTable(1)">Price<i class="fa fa-sort"></i></th>
                    <th>Description</th>
                    <th>Compatible Options</th>
                    <c:choose>
                        <c:when test="${table=='edit'}">
                            <th>Actions</th>
                        </c:when>
                        <c:otherwise>
                            <th>Choose Tariff</th>
                        </c:otherwise>
                    </c:choose>

                </tr>
                </thead>
                <tbody id="myTable">
                <c:forEach items="${tariffs}" var="tariff">
                    <tr>
                        <td>${tariff.name}</td>
                        <td>${tariff.price}</td>
                        <td>${tariff.description}</td>
                        <td>
                            <c:forEach items="${tariff.options}" var="option">
                                <li>${option.name}</li>
                            </c:forEach>
                        </td>
                        <td>

                            <c:choose>
                                <c:when test="${table=='edit'}">
                                    <a href="tariffs/edit/${tariff.id}" class="edit" title="Edit"  data-toggle="modal" data-target="#deleteModal" data-toggle="tooltip"  ><i
                                    class="material-icons">&#xE254;</i></a>
                                    <a href="#deleteModal"  class="delete" title="Delete" data-toggle="modal" data-toggle="tooltip" data-target="#deleteModal" data-id="${tariff.id}"><i
                                            class="material-icons">&#xE872;</i></a>
                                </c:when>
                                <c:otherwise>

                        <input type="checkbox" class="chk"/>&nbsp;
                                </c:otherwise>
                            </c:choose>


                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="col-md-12 text-center">
                <div class="hint-text">Showing <b>4</b> out of <b>${fn:length(tariffs)}</b> entries</div>
                <ul class="pagination pagination-lg pager" id="pagination"></ul>
            </div>

        </div>
    </div>
</div>
</div>

<!-- Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Delete Tariff</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure that you want to delete the tariff?
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

<script>
    $(document).ready(function () {

        $('#myTable').pageMe({pagerSelector: '#pagination', showPrevNext: true, hidePageNumbers: false, perPage: 4});

    });
</script>
<script>
    $('input.chk').on('change', function() {
        $('input.chk').not(this).prop('checked', false);
    });
</script>
<script>
    function sortTable(n) {
        var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
        table = document.getElementById("Table");
        switching = true;
        // Set the sorting direction to ascending:
        dir = "asc";
        /* Make a loop that will continue until
        no switching has been done: */
        while (switching) {
            // Start by saying: no switching is done:
            switching = false;
            rows = table.rows;
            /* Loop through all table rows (except the
            first, which contains table headers): */
            for (i = 1; i < (rows.length - 1); i++) {
                // Start by saying there should be no switching:
                shouldSwitch = false;
                /* Get the two elements you want to compare,
                one from current row and one from the next: */
                x = rows[i].getElementsByTagName("TD")[n];
                y = rows[i + 1].getElementsByTagName("TD")[n];
                /* Check if the two rows should switch place,
                based on the direction, asc or desc: */
                if (dir == "asc") {
                    if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                        // If so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                } else if (dir == "desc") {
                    if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                        // If so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                }
            }
            if (shouldSwitch) {
                /* If a switch has been marked, make the switch
                and mark that a switch has been done: */
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
                // Each time a switch is done, increase this count by 1:
                switchcount++;
            } else {
                /* If no switching has been done AND the direction is "asc",
                set the direction to "desc" and run the while loop again. */
                if (switchcount == 0 && dir == "asc") {
                    dir = "desc";
                    switching = true;
                }
            }
        }
    }
</script>
<script>
    $('#deleteModal').on('show.bs.modal', function(e) {

        //get data-id attribute of the clicked element
        var id = $(e.relatedTarget).data('id');

        //populate the textbox
        $(e.currentTarget).find('form[id="action"]').val(id);
        var $form = $('#action');
            $form.attr('action', 'tariffs/delete/' +  id);
    });
</script>
<script>
    $(document).ready(function() {
        $('#myTable').DataTable();
        $('td :checkbox').bind('change click', function () {
            $(this).closest('tr').toggleClass('highlight', this.checked);
        }).change();


    } );
</script>
</body>
</html>