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


<head>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../resource/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../resource/dist/js/bootstrap.min.js"></script>
    <script src="../resource/js/pagination.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>

</head>

<body>


<table class="table table-striped table-hover table-bordered" id="Table">
    <thead>
    <tr>
        <th>Name</th>
        <th>Contracts</th>
        <th>Passport number</th>
        <th>Birthday</th>
        <th>Address</th>
        <th>eMail</th>
    </tr>
    </thead>
    <tbody id="myTable">
    <c:forEach items="${clients}" var="client">
        <tr>
            <td>${client.name} ${client.surname}</td>
            <td>${client.phoneNumber}</td>
            <td>${client.passport}</td>
            <td>${client.birthday}</td>
            <td>${client.address.street}, ${client.address.houseNo}<br>
                    ${client.address.zip} ${client.address.city}<br>
                    ${client.address.country}
            <td>${client.email}</td>
        </tr>

    </c:forEach>
    </tbody>
</table>


<div class="col-md-12 text-center">
    <div class="hint-text">Showing <b>4</b> out of <b>${fn:length(tariffs)}</b> entries</div>
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