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


<head>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="${contextPath}/resource/js/pagination.js"></script>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
    <!--Search-->
    <script>
        $(document).ready(function () {
            sortTable();
            $('[data-toggle="tooltip"]').tooltip();
            $('#myTable').pageMe({pagerSelector: '#pagination', showPrevNext: true, hidePageNumbers: false, perPage: 4});
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


<table class="table table-striped table-hover table-bordered" id="Table">
    <thead>
    <tr>
        <th onclick="sortTable(0)">Name<i class="fa fa-sort"></i></th>
        <th onclick="sortTable(1)">Price<i class="fa fa-sort"></i></th>
        <th>Description</th>
        <c:if test="${table=='edit'}">
            <th>Compatible Options</th>

            <th>Validity</th>
        </c:if>
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
        <tr class="tableRow">
            <td>${tariff.name}</td>
            <td>${tariff.price}</td>
            <td>${tariff.description}</td>
            <c:if test="${table=='edit'}">
                <td>
                    <a href="/tariffs/optionList/${tariff.id}" class="add" title="View options"><i
                            class="material-icons">&#xE8f4;</i></a>
                    <c:forEach items="${tariff.options}" var="option">
                        <li>${option.name}</li>
                    </c:forEach>
                </td>
                <td>${tariff.isValid}</td>
            </c:if>
            <td>
                <c:choose>
                    <c:when test="${table=='edit'}">
                        <a href="/tariffs/edit/${tariff.id}" class="edit" title="Edit"><i
                                class="material-icons">&#xE254;</i></a>
                        <c:if test="${tariff.isValid==true}">
                            <a href="#deleteModal" class="delete" title="Delete" data-toggle="modal"
                              data-target="#deleteModal" data-id="${tariff.id}"><i
                                    class="material-icons">&#xE872;</i></a>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <input type="checkbox" class="chk" tariffName="${tariff.name}" price="${tariff.price}"
                               value="${tariff.id}" name="tariffID" id="${tariff.id}"
                        <c:forEach items="${existingTariffs}" var="t">
                            <c:if test="${tariff.id==t.id}"> checked </c:if>
                        </c:forEach>
                        />&nbsp;
                    </c:otherwise>
                </c:choose>


            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<div class="col-md-12 text-center">
    <div class="hint-text">Showing <b id="current">4</b> out of <b id="total"></b> entries</div>
    <ul class="pagination" id="pagination">
    </ul>
    <input type="hidden" id="page" value=0>
</div>


</body>