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
<%@ include file="elements/TopNavBar.jsp" %>
<%@ include file="elements/SideBar.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" href="${contextPath}/resource/images/favicon1.png">
    <!-- Bootstrap core CSS -->
    <link href="${contextPath}/resource/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resource/css/dashboard.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="${contextPath}/resource/dist/js/bootstrap.min.js"></script>


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
    <%-- Modal Script --%>
    <script>
        $(document).on('shown.bs.modal', '#deleteModal', function (e) {
            //get data-id attribute of the clicked element
            var id = $(e.relatedTarget).data('id');
            //populate the textbox
            $(e.currentTarget).find('form[id="action"]').val(id);
            var $form = $('#action');
            $form.attr('action', '/optionGroups/delete/' + id);
        });
    </script>
</head>

<body>

<div class="container-fluid">

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

        <h1 class="page-header">Option Groups</h1>


        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
                        <h2>Option Groups administration</h2>
                    </div>
                    <div class="col-sm-6">

                        <div class="search-box">
                            <i class="material-icons">&#xE8B6;</i>
                            <input type="text" class="form-control" id="myInput" placeholder="Search&hellip;">
                        </div>
                        <div class="newtariff">
                            <form action="/optionGroups/new">
                                <button type="submit" class="btn btn-success">Add Option Group</button>
                            </form>
                        </div>
                    </div>

                </div>
            </div>

            <table class="table table-striped table-hover table-bordered" id="Table">
                <thead>
                <tr>
                    <th onclick="sortTable(0)">Name<i class="fa fa-sort"></i></th>
                    <th>Description</th>
                    <th>Compatible Options</th>
                    <th>Validity</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody id="myTable">
                <c:forEach items="${optionGroups}" var="optionGroup">
                    <tr class="tableRow">
                        <td>${optionGroup.name}</td>
                        <td>${optionGroup.description}</td>
                        <td>
                            <c:forEach items="${optionGroup.options}" var="option">
                                <li>${option.name}</li>
                            </c:forEach>
                        </td>
                        <td>${optionGroup.isValid}</td>

                        <td>
                            <a href="/optionGroups/edit/${optionGroup.id}" class="edit" title="Edit"><i
                                    class="material-icons">&#xE254;</i></a>
                            <c:if test="${optionGroup.isValid==true}">
                                <a href="#deleteModal" class="delete" title="Delete" data-toggle="modal" data-target="#deleteModal" data-id="${optionGroup.id}"><i
                                        class="material-icons">&#xE872;</i></a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="col-md-12 text-center">
                <div class="hint-text">Showing <b>4</b> out of <b>${fn:length(optionGroups)}</b> entries</div>
                <ul class="pagination pagination-lg pager" id="pagination"></ul>
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
                        Are you sure that you want to delete the option group?<br>
                        Existing relations with options will be lost.
                    </div>
                    <div class="modal-footer">
                        <form> <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                        </form>

                        <form id="action" action="">
                            <button type="submit" class="btn btn-success">Yes</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>
<!--PaginationDao-->
<script>
    $(document).ready(function () {

        $('#myTable').pageMe({pagerSelector: '#pagination', showPrevNext: true, hidePageNumbers: false, perPage: 4});

    });
</script>

<!--Sort-->
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
<!--Tooltips-->
<script type="text/javascript">
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>
</body>
</html>