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
        <th >Monthly Price</th>
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
    <c:forEach items="${options}" var="o">
    <tr>
        <td>${o.name}</td>
        <td>${o.priceOneTime}</td>
        <td>${o.priceMonthly}</td>
        <td>${o.description}</td>
        <td>${o.optionGroup.name}</td>
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
        <td>
            <c:choose>
                <c:when test="${table=='edit'}">
                    <a href='/options/edit/' class='edit' title='Edit'><i class='material-icons'>&#xE254</i></a>
                    <c:if test="${o.isValid==true}">
                        <a href="#deleteModal" class="delete" title="Delete" data-toggle="modal"
                           data-target="#deleteModal" data-id="${o.id}"><i
                                class="material-icons">&#xE872;</i></a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <input type="checkbox" class="chk" value="${o.id}" price="${o.priceMonthly}"
                           priceOneTime="${o.priceOneTime}" optionName="${o.name}"
                           name="optionID"<c:forEach items="${existingOptions}" var="t">
                        <c:if test="${o.id==t.id}"> checked </c:if>
                    </c:forEach>/>&nbsp;
                </c:otherwise>
            </c:choose>
        </td>

        </c:forEach>

    </tbody>
</table>
<div class="col-md-12 text-center">
    <div class="hint-text">Showing <b id="current">4</b> out of <b id="total"></b> entries</div>
    <ul class="pagination" id="pagination">

    </ul>
    <input type="hidden" id="page" value=1>
</div>
</div>
<!--PaginationDao-->
<script>
    $(document).ready(function () {

        $('#myTable').pageMe({pagerSelector: '#pagination', showPrevNext: true, hidePageNumbers: false, perPage: 4});

    });
</script>
<!--Tooltips-->
<script type="text/javascript">
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>

</body>





