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

<!DOCTYPE html>

<head>
    <meta charset="utf-8">

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../resource/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../resource/dist/js/bootstrap.min.js"></script>
    <script src="../resource/js/pagination.js"></script>


</head>

<body>
<table class="table table-striped table-hover table-bordered" id="Table">
    <thead>
    <tr>
        <th onclick="sortTable(0)">Name<i class="fa fa-sort"></i></th>
        <th onclick="sortTable(1)">One Time Price<i class="fa fa-sort"></i></th>
        <th onclick="sortTable(2)">Monthly Price<i class="fa fa-sort"></i></th>

        <th>Description</th>
        <c:if test="${table=='edit'}">
            <th>Condition</th>
            <th>Is condition to</th>
            <th>Compatible Tariffs</th>
            <th>Validity</th>
        </c:if>

        <c:choose>
            <c:when test="${table=='edit'}">
                <th>Actions</th>
            </c:when>
            <c:otherwise>
                <th>Choose Options</th>
            </c:otherwise>
        </c:choose>
    </tr>
    </thead>
    <tbody id="myTable">
    <c:forEach items="${options}" var="o">
    <tr>
        <td>${o.name}</td>
        <td>${o.priceOneTime}</td>
        <td>${o.priceMonthly}</td>
        <td>${o.description}</td>
        <c:if test="${table=='edit'}">
        <td>${o.parent.name}</td>
        <td>
            <c:forEach items="${o.children}" var="t">
                <li>${t.name}</li>
            </c:forEach>
        </td>
        <td>
            <c:forEach items="${o.compatibleTariffs}" var="t">
                <li>${t.name}</li>
            </c:forEach>
        </td>
        <td>${o.isValid}</td>
        </c:if>
        <td>
            <c:choose>
                <c:when test="${table=='edit'}">
                    <a href="/options/edit/${o.id}" class="edit" title="Edit"><i
                            class="material-icons">&#xE254;</i></a>
                    <c:if test="${o.isValid==true}">
                        <a href="#deleteModal" class="delete" title="Delete" data-toggle="modal"
                           data-toggle="tooltip" data-target="#deleteModal" data-id="${o.id}"><i
                                class="material-icons">&#xE872;</i></a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <input type="checkbox" class="chk" value="${o.id}" price="${o.priceMonthly}" optionName="${o.name}"
                           name="optionID"/>&nbsp;
                </c:otherwise>
            </c:choose>
        </td>

        </c:forEach>

    </tbody>
</table>
<div class="col-md-12 text-center">
    <div class="hint-text">Showing <b>4</b> out of <b>${fn:length(options)}</b> entries</div>
    <ul class="pagination pagination-lg pager" id="pagination"></ul>
</div>
<!--Pagination-->
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





