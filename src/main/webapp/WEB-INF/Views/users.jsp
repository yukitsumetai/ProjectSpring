<%--
  Created by IntelliJ IDEA.
  User: ekochuro
  Date: 03.03.2019
  Time: 0:02
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Existing Users</title>
</head>
<body>
<ul>
    Hello!
    <c:forEach items="${clients}" var="client">
        <li>
            <td>${client.name}</td>
            <td>${client.birthday}</td>
            <td>${client.address.street}</td>
        </li>
    </c:forEach>

</ul>
</body>
</html>
