<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 03.03.2019
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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


    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>
</head>
<body>

<h2>Options</h2>
<p>List of current options:</p>
<input id="myInput" type="text" placeholder="Search..">
<br><br>

<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>One Time Price</th>
        <th>Monthly</th>
        <th>Description</th>
        <th>Compatible Tariffs</th>
        <th>/Edit/Delete</th>
    </tr>
    </thead>
    <tbody id="myTable">
    <c:forEach items="${options}" var="o">
        <tr>
            <td>${o.name}</td>
            <td>${o.priceOneTime}</td>
            <td>${o.priceMonthly}</td>
            <td>${o.description}</td>
            <td>
                <c:forEach items="${o.compatibleTariffs}" var="t">
                <li>${t.name}</li>
            </c:forEach>
            </td>>
            <td ng-controller="myController"><a
                    href="getProductById/${client.id}" class="btn btn-info"
                    role="button"> <span class="glyphicon glyphicon-info-sign"></span></a>
                <a href="tariffs/newTariff"
                   class="btn btn-success" style="margin-left: 5px"> <span
                        class="glyphicon glyphicon-edit"></span></a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>

