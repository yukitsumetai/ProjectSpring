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

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../resource/assets/js/vendor/jquery.min.js"><\/script>')</script>
</head>

<body>
<table class="table table-striped table-hover table-bordered" id="Table">
    <thead>
    <tr>
        <th>Name</th>
        <th>One Time Price</th>
        <th>Monthly Price</th>
        <th>Description</th>
        <th>Option
            Group
        </th>
        <th>Condition</th>
        <th>Is condition to</th>
        <th>Compatible
            Tariffs
        </th>
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



    </tbody>
</table>
<div class="col-md-12 text-center">
    <div class="hint-text">Showing <b id="current">4</b> out of <b id="total"></b> entries</div>
    <ul class="pagination" id="pagination">
    </ul>
    <input type="hidden" id="page" value=0>

</div>
<div id="checked">
    <c:forEach items="${existing}" var="e">
        <input name="optionID2" class="opt2" type="hidden" value=${e.id}>
    </c:forEach>
</div>
</div>
<!--highlight-->
<script>

    $(document).on('click', '.chk', function() {
        var checkboxes = document.getElementsByClassName('chk');
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) checkboxes[i].closest('tr').classList.add("highlight");
            else checkboxes[i].closest('tr').classList.remove("highlight");
        }
    });
</script>

</body>





