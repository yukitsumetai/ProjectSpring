<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 03.03.2019
  Time: 0:02
  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
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

<h2>Tariffs</h2>
<p>List of current tariffs:</p>
<input id="myInput" type="text" placeholder="Search..">
<br><br>

<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Surname</th>
        <th>Birthday</th>
        <th>eMail</th>
        <th>Address</th>
    </tr>
    </thead>
    <tbody id="myTable">
    <c:forEach items="${clients}" var="client">
        <tr>
            <td>${client.name}</td>
            <td>${client.surname}</td>
            <td>${client.birthday}</td>
            <td>${client.email}</td>
            <td>
                <c:forEach items="${client.address}" var="address">
                    <li>${address.city}</li>
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